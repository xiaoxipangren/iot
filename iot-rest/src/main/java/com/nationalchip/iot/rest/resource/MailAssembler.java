package com.nationalchip.iot.rest.resource;

import com.nationalchip.iot.cache.helper.KeyHelper;
import com.nationalchip.iot.cache.redis.IRedisService;
import com.nationalchip.iot.message.mail.IMail;
import com.nationalchip.iot.message.mail.SimpleMail;
import com.nationalchip.iot.rest.configuration.RestProperty;
import com.nationalchip.iot.rest.controller.MailController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/3/18 10:26 AM
 * @Modified:
 */
@Component
public class MailAssembler extends ResourceAssemblerSupport<IMail,MailResource> {

    private static final String VALIDATE_EMAIL_TITLE ="国芯开发者账号注册验证码";
    private static final String RESETPWD_EMAIL_TITLE ="国芯开发者账号重置密码验证码";
    private static final String ACTION_VALIDATE="validate";
    private static final String ACTION_RESETPWD="resetpwd";

    @Autowired
    private IRedisService  redisService;

    @Autowired
    private RestProperty restProperty;

    public MailAssembler() {
        super(MailController.class, MailResource.class);
    }

    @Override
    public MailResource toResource(IMail entity) {
        MailResource mailResource = new MailResource();
        mailResource.setMessage(String.format("发送\"%s\"成功"));
        mailResource.setTo(entity.getTo());

        return mailResource;
    }

    public IMail fromRequest(MailRequest request){
        SimpleMail mail = new SimpleMail("");
        return mail;
    }


    private String generateValidateContent(String email){
        String code = generateCacheCode(generateCacheKey(email,ACTION_VALIDATE));
        return String.format(restProperty.getValidateMail(),code);

    }

    private String generateCacheCode(String key){
        String code = UUID.randomUUID().toString().replace("-","").toLowerCase().substring(0,4);
        redisService.set(key.toLowerCase(),code,restProperty.getValidationExpiration(), TimeUnit.SECONDS);
        return code;
    }



    private String generateResetPasswordContent(String email){
        String code = generateCacheCode(generateCacheKey(email,ACTION_RESETPWD));

        return String.format(restProperty.getResetpwdMail(),code);

    }


    private String generateCacheKey(String email,String prefix){
        if(prefix.equalsIgnoreCase(ACTION_RESETPWD))
            return KeyHelper.resetPasswordKey(email);
        else if(prefix.equalsIgnoreCase(ACTION_VALIDATE))
            return KeyHelper.validateEmailKey(email);
        return String.format("%s-%s",prefix,email);
    }



}
