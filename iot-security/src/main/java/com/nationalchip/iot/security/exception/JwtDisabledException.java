package com.nationalchip.iot.security.exception;

import com.nationalchip.iot.exception.UncheckedException;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/26/18 11:00 AM
 * @Modified:
 */
public class JwtDisabledException extends UncheckedException {
    public JwtDisabledException(){
        super("Jwt已注销，请重新登录获取token。");
    }
}
