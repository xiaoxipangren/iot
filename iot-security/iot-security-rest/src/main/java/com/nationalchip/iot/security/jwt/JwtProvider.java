package com.nationalchip.iot.security.jwt;

import com.nationalchip.iot.security.authentication.AuthenticationDetails;
import com.nationalchip.iot.security.configuration.RestSecurityProperty;
import com.nationalchip.iot.security.exception.JwtExpiratedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

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



    @Override
    public String toToken(IClaimsBuilder builder) {

        Claims claims = builder.build();
        String subject = builder.getSubject();

        String jwt = generateToken(subject,claims);

        return jwt;

    }

    @Override
    public Authentication fromToken(String token) {

        Claims claims = parseToken(token);

        String username = claims.getSubject();
        Collection<String> authorities = (Collection<String>) claims.get(AUTHORTIES);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,token,authorities.stream().map(a -> new SimpleGrantedAuthority(a)).collect(Collectors.toList()));
        Date expiration = claims.getExpiration();
        AuthenticationDetails details = new AuthenticationDetails(username,expiration);
        details.setOnce(claims.get(ONCE)==null?false:(boolean)claims.get(ONCE));
        authentication.setDetails(details);

        return  authentication;

    }

    @Override
    public String refresh(String token) {

        return null;
    }

    private String generateToken(String subject, Claims claims){


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


    private Claims parseToken(String token){
        try{
            Claims claims = Jwts.parser().setSigningKey(restSecurityProperty.getJwt().getSecret())
                    .parseClaimsJws(token).getBody();
            if(validate(claims)){
                return claims;
            }
        }catch (ExpiredJwtException e){
            throw e;
        }

        return null;
    }



    private boolean validate(Claims claims){
        Date issued = claims.getIssuedAt();
        Date expirated = claims.getExpiration();
        Date now =new Date(System.currentTimeMillis());
        if( issued.after(now) || expirated.before(now))
            throw new JwtExpiratedException();

        return true;
    }



}
