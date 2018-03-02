package com.nationalchip.iot.security.exception;

import com.nationalchip.iot.exception.UncheckedException;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/26/18 10:58 AM
 * @Modified:
 */
public class JwtExpiratedException extends UncheckedException{
    public JwtExpiratedException(){
        super("Jwt已过期，请重新获取token。");
    }
}
