package com.nationalchip.iot.security.jwt;

import org.springframework.security.core.Authentication;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/19/18 3:43 PM
 * @Modified:
 */
public interface IJwtProvider {
    String AUTHORTIES="authorties";
    String DISABLED="disabled";
    String ONCE = "once";

    String toToken(IClaimsBuilder builder);
    Authentication fromToken(String token);
    String refresh(String token);


}
