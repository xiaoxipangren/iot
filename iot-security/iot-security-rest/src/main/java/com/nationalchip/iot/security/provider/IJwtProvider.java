package com.nationalchip.iot.security.provider;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.Map;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/19/18 3:43 PM
 * @Modified:
 */
public interface IJwtProvider {
    public static final String AUTHORTIES="authorties";
    public static final String DISABLED="disabled";

    String generateToken(String subject,Claims claims);

    Claims parseToken(String token);

    String refreshToken(String oldToken);

}
