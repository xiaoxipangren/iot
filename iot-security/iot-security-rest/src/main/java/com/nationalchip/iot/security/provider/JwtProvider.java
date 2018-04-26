package com.nationalchip.iot.security.provider;

import com.nationalchip.iot.data.model.auth.IAuthority;
import com.nationalchip.iot.data.model.auth.User;
import com.nationalchip.iot.security.configuration.RestSecurityProperty;
import com.nationalchip.iot.security.exception.JwtDisabledException;
import com.nationalchip.iot.security.exception.JwtExpiratedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/26/18 9:02 AM
 * @Modified:
 */

@Component
public class  JwtProvider implements IJwtProvider{

    @Autowired
    private RestSecurityProperty restSecurityProperty;

    private Date expiration;




    public String generateToken(String subject, Claims claims){


        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setIssuer(restSecurityProperty.getJwt().getIssuer())
                .setExpiration(new Date(now+ Duration.parse(restSecurityProperty.getJwt().getExpiration()).toMillis()))
                .addClaims(claims)
                .signWith(SignatureAlgorithm.valueOf(restSecurityProperty.getJwt().getAlg()), restSecurityProperty.getJwt().getSecret())
                .compact();
    }


    public Claims parseToken(String token){
        try{
            Claims claims = Jwts.parser().setSigningKey(restSecurityProperty.getJwt().getSecret())
                    .parseClaimsJws(token).getBody();
            if(validate(claims)){
                return claims;
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

    @Override
    public String refreshToken(String oldToken) {
        return null;
    }


    private boolean validate(Claims claims){
        Date issued = claims.getIssuedAt();
        Date expirated = claims.getExpiration();
        this.expiration=expirated;
        Date now =new Date(System.currentTimeMillis());
        if( issued.after(now) || expirated.before(now))
            throw new JwtExpiratedException();

        boolean disabled = claims.get(DISABLED,Boolean.class);
        if(disabled)
            throw new JwtDisabledException();

        return true;
    }



}
