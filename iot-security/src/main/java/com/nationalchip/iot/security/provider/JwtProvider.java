package com.nationalchip.iot.security.provider;

import com.nationalchip.iot.data.model.IAuthority;
import com.nationalchip.iot.data.model.User;
import com.nationalchip.iot.security.configuration.SecurityProperty;
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

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/26/18 9:02 AM
 * @Modified:
 */

@Component
public class JwtProvider {

    @Autowired
    private SecurityProperty securityProperty;

    private static final String AUTHORTIES="authorties";
    private static final String DISABLED="disabled";


    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();


        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(user.getTenant())
                .setIssuedAt(new Date(now))
                .setIssuer(securityProperty.getIssuer())
                .setExpiration(new Date(now+ Duration.parse(securityProperty.getExpiration()).toMillis()))
                .claim(AUTHORTIES,user.getAuthorities())
                .claim(DISABLED,false)
                .signWith(SignatureAlgorithm.valueOf(securityProperty.getAlg()),securityProperty.getSecret())
                .compact();


    }


    public Authentication parseToken(String token){
        Claims claims = Jwts.parser().setSigningKey(securityProperty.getSecret())
                .parseClaimsJws(token).getBody();
        if(validate(claims)){
            String tenant = claims.getSubject();
            User user = new User();
            user.setTenant(tenant);
            user.setUsername(tenant);

            Object authorities = claims.get(AUTHORTIES);
            if(authorities!=null)
                user.setAuthorities(new HashSet<IAuthority>((Collection<? extends IAuthority>) authorities));

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());

            return authentication;

        }

        return null;

    }



    private boolean validate(Claims claims){
        Date issued = claims.getIssuedAt();
        Date expirated = claims.getExpiration();
        Date now =new Date(System.currentTimeMillis());
        if( issued.after(now) || expirated.before(now))
            throw new JwtExpiratedException();

        boolean disabled = claims.get(DISABLED,Boolean.class);
        if(disabled)
            throw new JwtDisabledException();

        return true;
    }


}
