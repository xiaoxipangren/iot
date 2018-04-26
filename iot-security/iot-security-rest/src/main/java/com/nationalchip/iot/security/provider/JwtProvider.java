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

    private static final String AUTHORTIES="authorties";
    private static final String DISABLED="disabled";


    public String generateToken(Authentication authentication){

        if(authentication==null)
            return null;


        User user = (User) authentication.getPrincipal();

        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(user.getTenant())
                .setIssuedAt(new Date(now))
                .setIssuer(restSecurityProperty.getJwt().getIssuer())
                .setExpiration(new Date(now+ Duration.parse(restSecurityProperty.getJwt().getExpiration()).toMillis()))
                .claim(AUTHORTIES,user.getAuthorities())
                .claim(DISABLED,false)
                .signWith(SignatureAlgorithm.valueOf(restSecurityProperty.getJwt().getAlg()), restSecurityProperty.getJwt().getSecret())
                .compact();
    }


    public Authentication parseToken(String token){
        try{
            Claims claims = Jwts.parser().setSigningKey(restSecurityProperty.getJwt().getSecret())
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
        }catch (Exception e){
            return null;
        }


        return null;

    }

    @Override
    public Date getExpiration(String token) {
        if(expiration!=null)
            return expiration;
        Claims claims = Jwts.parser().setSigningKey(restSecurityProperty.getJwt().getSecret())
                .parseClaimsJws(token).getBody();
        return  claims.getExpiration();
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
