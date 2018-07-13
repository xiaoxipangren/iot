package com.nationalchip.iot.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.nationalchip.iot.security.jwt.IJwtProvider.*;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/5/18 1:50 PM
 * @Modified:
 */
public class ClaimsBuilder implements IClaimsBuilder {


    private Map<String,Object> map = new HashMap<>();
    private String subject;

    @Override
    public IClaimsBuilder authorities(Collection<String> authorities) {
        map.put(AUTHORTIES,authorities);
        return self();
    }

    @Override
    public IClaimsBuilder subject(String subject) {
        this.subject = subject;
        return self();
    }

    @Override
    public IClaimsBuilder put(String key, String value) {
        this.map.put(key,value);
        return self();
    }

    @Override
    public IClaimsBuilder once(boolean once) {
        this.map.put(ONCE,once);
        return  self();
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public Claims build() {
        Claims claims = new DefaultClaims();

        map.keySet().stream().forEach( key-> claims.put(key,map.get(key)));

        return claims;
    }


    private IClaimsBuilder self(){
        return this;
    }
}
