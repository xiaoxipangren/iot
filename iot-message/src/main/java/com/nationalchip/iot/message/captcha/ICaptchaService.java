package com.nationalchip.iot.message.captcha;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/3/18 4:32 PM
 * @Modified:
 */
public interface ICaptchaService {

    /**
     * 生成验证码
     * @param builder
     * @return
     */
    ICaptcha create(ICaptchaBuilder builder);

    ICaptcha find(String key);

    byte[] toImage(String key);

    ICaptcha validate(ICaptchaBuilder builder);

}
