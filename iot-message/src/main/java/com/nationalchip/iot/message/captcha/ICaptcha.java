package com.nationalchip.iot.message.captcha;

import java.time.LocalDateTime;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/4/18 2:14 PM
 * @Modified:
 */
public interface ICaptcha {
    String getKey();
    String getValue();
    LocalDateTime getDate();
    boolean isImaged();
    String getAction();
    String getTarget();
}
