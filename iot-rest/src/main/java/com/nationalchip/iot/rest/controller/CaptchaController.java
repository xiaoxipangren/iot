package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.helper.RegexHelper;
import com.nationalchip.iot.message.captcha.CaptchaBuilder;
import com.nationalchip.iot.message.captcha.ICaptcha;
import com.nationalchip.iot.message.captcha.ICaptchaBuilder;
import com.nationalchip.iot.message.captcha.ICaptchaService;
import com.nationalchip.iot.message.exception.CaptchaException;
import com.nationalchip.iot.message.exception.CaptchaExpiredException;
import com.nationalchip.iot.message.mail.IMailBuilder;
import com.nationalchip.iot.message.mail.IMailService;
import com.nationalchip.iot.message.mail.MailBuilder;
import com.nationalchip.iot.rest.configuration.RestProperty;
import com.nationalchip.iot.rest.exception.RestException;
import com.nationalchip.iot.rest.resource.*;
import com.nationalchip.iot.security.authority.Authority;
import static com.nationalchip.iot.security.configuration.RestMapping.*;
import com.nationalchip.iot.security.jwt.ClaimsBuilder;
import com.nationalchip.iot.security.jwt.IClaimsBuilder;
import com.nationalchip.iot.security.jwt.IJwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/3/18 4:08 PM
 * @Modified:
 */
@RestController
@RequestMapping(REST_BASE_MAPPING+ REST_CAPTCHA_MAPPING)
public class CaptchaController {

    private static final String VALIDATE_EMAIL_TITLE ="国芯开发者账号注册验证码";
    private static final String RESETPWD_EMAIL_TITLE ="国芯开发者账号重置密码验证码";

    @Autowired
    private ICaptchaService captchaService;

    @Autowired
    private IMailService mailService;

    @Autowired
    private IJwtProvider jwtProvider;

    @Autowired
    private RestProperty restProperty;

    @Autowired
    private CaptchaAssembler captchaAssembler;
    //生成验证码
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Response> create(@RequestBody CaptchaRequest request){

        ICaptchaBuilder builder = new CaptchaBuilder()
                .action(request.getAction())
                .target(request.getTarget());

        ICaptcha captcha;
        try{
            captcha = captchaService.create(builder);
        }catch (CaptchaException e){
            throw new RestException(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        CaptchaResponse response = captchaAssembler.toResource(captcha);

        String address = request.getAddress();

        if(RegexHelper.isEmail(address)){
            sendMail(request.getAction().toUpperCase(),request.getAddress(),captcha.getValue());
        }

        return Response.ok(response);
    }


    private void sendMail(String action,String address,String captcha){
        IMailBuilder mailBuilder = new MailBuilder()
                .to(address)
                .html(true);
        if(Authority.AUTH_REGISTER.equalsIgnoreCase(action)){
            mailBuilder.content(String.format(restProperty.getValidateMail(), captcha))
                    .subject(VALIDATE_EMAIL_TITLE);
        }
        else if(Authority.AUTH_RESET_PASSWORD.equalsIgnoreCase(action)){
            mailBuilder.content(String.format(restProperty.getResetpwdMail(), captcha))
                    .subject(RESETPWD_EMAIL_TITLE);
        }

        mailService.send(mailBuilder);
    }


    @RequestMapping(value = REST_ID_MAPPING,method = RequestMethod.GET)
    public ResponseEntity<byte[]> get(@PathVariable(value = "id")String id){
        try {
            byte[] bytes = captchaService.toImage(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(bytes,headers,HttpStatus.OK);
        } catch (CaptchaException | CaptchaExpiredException e) {
            throw new RestException("获取验证码图片错误:" + e.getMessage());
        }

    }


    //验证验证码，验证成功发放临时token
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Response> get(@RequestParam(value = "key") String key,
                                        @RequestParam(value = "code")String code){

        if(!key.contains(ICaptchaBuilder.SEPARATOR))
            throw new RestException("验证码key不合法",HttpStatus.BAD_REQUEST);

        String[] array = key.split(ICaptchaBuilder.SEPARATOR);

        ICaptchaBuilder builder = new CaptchaBuilder()
                .action(array[0])
                .target(array[1])
                .value(code);

        ICaptcha captcha=null;

        try{
            captcha = captchaService.validate(builder);
        }catch (CaptchaExpiredException e){
            throw new RestException(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        if(captcha!=null){
            IClaimsBuilder claimsBuilder = new ClaimsBuilder();
            claimsBuilder.authorities(Collections.singletonList(captcha.getAction().toUpperCase()));
            claimsBuilder.subject(captcha.getTarget());
            claimsBuilder.once(true);

            String token = jwtProvider.toToken(claimsBuilder);
            AuthResource auth = new AuthResource();
            auth.setUsername(captcha.getTarget());
            auth.setToken(token);

            return Response.ok(auth);
        }
        else{
            throw new RestException("验证码错误",HttpStatus.BAD_REQUEST);
        }



    }


}
