package com.nationalchip.iot.security.jwt;

import io.jsonwebtoken.Claims;

import java.util.Collection;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/5/18 11:12 AM
 * @Modified:
 */
public interface IClaimsBuilder {
    IClaimsBuilder authorities(Collection<String> authorities);
    IClaimsBuilder subject(String subject);
    IClaimsBuilder put(String key,String value);
    IClaimsBuilder once(boolean once);

    String getSubject();

    Claims build();
}
