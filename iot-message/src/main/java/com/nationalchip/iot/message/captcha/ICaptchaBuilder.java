package com.nationalchip.iot.message.captcha;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/4/18 2:36 PM
 * @Modified:
 */
public interface ICaptchaBuilder {
    String SEPARATOR ="-";

    ICaptchaBuilder value(String value);
    ICaptchaBuilder action(String action);
    ICaptchaBuilder target(String target);

    ICaptcha build();

}
