package com.nationalchip.iot.security.provider;

import org.springframework.security.core.Authentication;

import java.util.Date;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/19/18 3:43 PM
 * @Modified:
 */
public interface IJwtProvider {
    String generateToken(Authentication authentication);

    Authentication parseToken(String token);

    Date getExpiration(String token);
}
