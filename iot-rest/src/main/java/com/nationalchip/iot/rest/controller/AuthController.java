package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.builder.IBuilderFactory;
import com.nationalchip.iot.data.builder.IUserBuilder;
import com.nationalchip.iot.data.manager.IUserManager;
import com.nationalchip.iot.data.model.auth.IUser;
import com.nationalchip.iot.helper.RegexHelper;
import com.nationalchip.iot.rest.exception.AuthException;
import com.nationalchip.iot.rest.exception.RestException;
import com.nationalchip.iot.rest.model.RestResult;
import com.nationalchip.iot.rest.model.auth.*;
import com.nationalchip.iot.rest.service.AuthService;
import com.nationalchip.iot.rest.service.MailService;
import com.nationalchip.iot.security.configuration.RestConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = RestConstant.REST_BASE_MAPPING+RestConstant.REST_AUTH_MAPPING)
public class AuthController extends BaseController{

    private final static String USER_NAME="username";
    private final static String USER_PHONE="phone";
    private final static String USER_EMAIL="email";
    private static final String VALIDATE_EMAIL_TITLE ="国芯开发者账号激活验证码";
    private static final String RESETPWD_EMAIL_TITLE ="国芯开发者账号重置密码验证码";
    private static final String ACTION_VALIDATE="validate";
    private static final String ACTION_RESETPWD="resetpwd";

    @Autowired
    private IBuilderFactory builderFactory;
    @Autowired
    private IUserManager userManager;
    @Autowired
    private MailService mailService;
    @Autowired
    private AuthService authService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Value("${iot.rest.activation.expiration}")
    private int validationExpiration;

//    @ApiOperation(value ="注册新用户",notes = "根据输入的用户名、邮箱和密码注册新用户")
//    @ApiImplicitParam(name = "user",value = "注册用户",paramType = "body",dataType = "InputUser")
    @RequestMapping(value = RestConstant.REST_REGISTER_ACTION,method= RequestMethod.POST,consumes="application/json",produces="application/json;charset=UTF-8")
    public ResponseEntity<RestResult> register(@RequestBody RegisterUser user){
        validateCode(user.getEmail(),ACTION_VALIDATE,user.getCode());
        boolean result = registerUser(user);
        return ok("注册成功");

    }

    @RequestMapping(value = RestConstant.REST_VALIDATE_ACTION,method = RequestMethod.POST)
    public ResponseEntity<RestResult> validateCode(@RequestBody UserEmail email){
        return ResponseEntity.ok(new RestResult(validateCode(email.getEmail(),email.getAction(),email.getCode())));
    }



    @RequestMapping(value = RestConstant.REST_SENDMAIL_ACTION,method= RequestMethod.POST,consumes="application/json",produces="application/json;charset=UTF-8")
    public ResponseEntity<RestResult> sendMail(@RequestBody UserEmail email){


        if(email.getAction().toLowerCase().equals(ACTION_VALIDATE)){
            sendValidateEmail(email.getEmail(),email.getUsername());
        }
        else if(email.getAction().equals(ACTION_RESETPWD)){
            sendResetPasswordEmail(email.getEmail());
        }

        return ok("发送激活邮件成功",true);
    }


    @RequestMapping(value = RestConstant.REST_LOGIN_ACTION,method= RequestMethod.POST)
    public ResponseEntity<RestResult> login(@RequestBody InputUser login){

        UserInfo user = authService.login(login.getUsername(),login.getPassword());

        return ok("登录成功",user);
    }


    @RequestMapping(value = "/logout")
    public ResponseEntity<RestResult> logout(){

        boolean result = authService.logout();

        return ok(result);
    }


    @RequestMapping(value = RestConstant.REST_EXISTS_ACTION+"/{prop}/{value:.+}")
    public ResponseEntity<RestResult> exists(@PathVariable String prop, @PathVariable String value){
        boolean result = false;
        if(prop.toLowerCase().equals(USER_NAME)){
            result = userManager.userExists(value);
        }
        else if(prop.toLowerCase().equals(USER_EMAIL)){
            result = userManager.existsByEmail(value);
        }
        else if(prop.toLowerCase().equals(USER_PHONE)){
            result = userManager.existsByPhone(value);
        }
        else{
            throw new RestException(String.format("无法识别的参数：%s",prop),HttpStatus.BAD_REQUEST);
        }

        return ok(new UserExists(result));
    }


    @RequestMapping(value ="/changepwd",method = RequestMethod.POST)
    public ResponseEntity<RestResult> changePassword(@RequestBody InputUser changePwd){
        try{
            userManager.changePassword(changePwd.getPassword(),changePwd.getNewPwd());
            return ok("密码修改成功",true);
        }
        catch (Exception e){
            throw new AuthException(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value = RestConstant.REST_RESETPWD_ACTION,method=RequestMethod.POST)
    public ResponseEntity<RestResult> resetPassword(@RequestBody PwdReset reset){
        validateCode(reset.getEmail(),ACTION_RESETPWD,reset.getCode());
        try{
            userManager.resetPassword(reset.getEmail(),reset.getPassword());
        }
        catch (Exception e){
            throw new AuthException("重置密码失败");
        }
        return ok("重置密码成功",true);
    }

    @RequestMapping(value = "/changemail")
    public ResponseEntity<RestResult> changeEmail(InputUser user){
        String email = user.getEmail();
        if(!RegexHelper.isEmail(email)){
            throw new AuthException(String.format("新邮箱地址不合法，修改失败"));
        }

        try{

            IUserBuilder builder=builderFactory.user();
            builder.email(user.getEmail());
            userManager.update(builder);
            //userManager.changeEmail(email);
            return ok("邮箱修改成功",true);
        }
        catch (Exception e){
            throw new AuthException(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    private boolean registerUser(RegisterUser user){

        try{

            IUserBuilder builder = builderFactory.user();
            builder.email(user.getEmail())
                    .password(user.getPassword())
                    .phone(user.getPhone())
                    .type(user.getType())
                    .name(user.getUsername());

            userManager.create(builder);

            return true;
        }
        catch (Exception e){
            throw new RestException(e.getMessage());
        }
    }


    private boolean sendResetPasswordEmail(String email){

        if(!RegexHelper.isEmail(email))
            throw new AuthException(String.format("邮箱%s格式不正确",email),HttpStatus.BAD_REQUEST);

        IUser user=null;
        try{
            user = userManager.findByEmail(email);
            if(user==null)
                throw new AuthException(String.format("邮箱%s未注册",email));
        }catch (Exception e){
            throw new AuthException(e.getMessage());
        }

        String content = generateResetPasswordContent(user.getUsername(),email);
        mailService.sendSimple(email, RESETPWD_EMAIL_TITLE,content,true);
        return true;



    }

    private boolean sendValidateEmail(String email,String username){

        if(!RegexHelper.isEmail(email))
            throw new AuthException(String.format("邮箱%s格式不正确",email),HttpStatus.BAD_REQUEST);

        if(userManager.existsByEmail(email)){
            throw new AuthException(String.format("邮箱%s已被绑定",email),HttpStatus.BAD_REQUEST);
        }



        String content = generateValidateContent(email,username);

        mailService.sendSimple(email, VALIDATE_EMAIL_TITLE,content,true);

        return true;

    }

    private String generateValidateContent(String email,String username){
        String code = generateCacheCode(generateCacheKey(email,ACTION_VALIDATE));
        return String.format("尊敬的国芯开发者%s<br>你好！<br>欢迎注册成为国芯开发者，您的邮箱激活验证码为<br>%s<br>，请将其复制到注册页面，完成注册流程。",username,code);

    }

    private String generateCacheCode(String key){
        String code = UUID.randomUUID().toString().replace("-","").toLowerCase().substring(0,4);
        redisTemplate.opsForValue().set(key.toLowerCase(),code,validationExpiration, TimeUnit.SECONDS);
        return code;
    }

    private String generateClickUrl(String url, String code){
        StringBuilder sb = new StringBuilder(url);
        return sb.append(String.format("?code=%s",code))
                .toString();
    }

    private boolean activateUser(String code){

        if(redisTemplate.hasKey(code)){
            String username = (String)redisTemplate.opsForValue().get(code);
            if(username!=null){
                try{
                    userManager.activateUser(username);
                }
                catch (Exception e){
                    throw new RestException("激活失败");
                }
                return true;
            }
            else{
                throw new RestException(String.format("激活码错误"),HttpStatus.BAD_REQUEST);
            }
        }
        else{
            throw new RestException(String.format("激活码已过期"),HttpStatus.BAD_REQUEST);
        }

    }


    private String generateResetPasswordContent(String username,String email){
        String code = generateCacheCode(generateCacheKey(email,ACTION_RESETPWD));

        return String.format("尊敬的国芯开发者%s<br>你好！<br>重置密码的验证码为<br>%s<br>请输入到重置密码页面以完成密码重置流程。",username,code);

    }


    private String generateCacheKey(String email,String prefix){
        return String.format("%s-%s",prefix,email);
    }

    private boolean validateCode(String email,String prefix,String code){
        String key = generateCacheKey(email,prefix);
        if(!redisTemplate.hasKey(key))
            throw new AuthException("验证码已过期");
        String cacheCode = (String)redisTemplate.opsForValue().get(key);
        if(cacheCode == null)
            throw new AuthException("验证码不存在");
        return cacheCode.equalsIgnoreCase(code);
    }

}
