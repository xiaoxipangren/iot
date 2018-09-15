package com.nationalchip.iot.message.captcha;

import com.google.code.kaptcha.Producer;
import com.nationalchip.iot.cache.redis.IRedisService;
import com.nationalchip.iot.message.configuration.MessageProperty;
import com.nationalchip.iot.message.exception.CaptchaException;
import com.nationalchip.iot.message.exception.CaptchaExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/3/18 4:31 PM
 * @Modified:
 */
@Component
public class CaptchaService implements ICaptchaService {

    @Autowired
    private IRedisService redisService;

    @Autowired
    private Producer producer;

    @Autowired
    private MessageProperty messageProperty;



    @Override
    public ICaptcha create(ICaptchaBuilder builder) {

        ICaptcha captcha = builder.build();

        if(redisService.hasKey(captcha.getKey()) && redisService.get(captcha.getKey()) instanceof ICaptcha){
            ICaptcha cachedCaptcha = (ICaptcha)redisService.get(captcha.getKey());
            LocalDateTime date = cachedCaptcha.getDate();
            long wait = messageProperty.getCaptcha().getInterval()-Duration.between(date,LocalDateTime.now()).toMillis()/1000;
            if(wait>0)
                throw new CaptchaException(String.format("发送验证码过于频繁，请等待%ds后重试",wait));
        }

        String code = producer.createText();
        ((Captcha)captcha).setValue(code);

        redisService.set(captcha.getKey(),captcha,messageProperty.getCaptcha().getExpiration());

        return captcha;
    }

    @Override
    public ICaptcha find(String key) {

        checkExpiration(key);

        ICaptcha captcha = (ICaptcha) redisService.get(key);
        return captcha;
    }

    @Override
    public byte[] toImage(String key) {

        ICaptcha captcha = find(key);

        if(!captcha.isImaged()){
            return new byte[0];
        }

        BufferedImage bufferedImage = producer.createImage(captcha.getValue());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage,"png",outputStream);

            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new CaptchaException("生成图片验证码发生错误");
        }
    }

    @Override
    public ICaptcha validate(ICaptchaBuilder builder) {
        ICaptcha captcha = builder.build();
        ICaptcha storedCaptcha = find(captcha.getKey());

        if(captcha.equals(storedCaptcha)){
            //验证码只能使用一次
            redisService.delete(captcha.getKey());
            return storedCaptcha;
        }
        return null;
    }

    private void checkExpiration(String key){
        if(!redisService.hasKey(key))
            throw new CaptchaExpiredException();
    }

}
