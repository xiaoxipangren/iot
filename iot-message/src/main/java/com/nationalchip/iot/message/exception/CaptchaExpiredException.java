package com.nationalchip.iot.message.exception;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/4/18 3:29 PM
 * @Modified:
 */
public class CaptchaExpiredException extends MessageException {
    public CaptchaExpiredException() {
        super("验证码不存在或已过期，请刷新验证码");
    }
}
