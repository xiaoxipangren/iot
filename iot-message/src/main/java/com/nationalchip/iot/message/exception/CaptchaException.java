package com.nationalchip.iot.message.exception;

import java.security.MessageDigest;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/3/18 7:40 PM
 * @Modified:
 */
public class CaptchaException extends MessageException {
    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }
}
