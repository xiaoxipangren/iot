package com.nationalchip.iot.rest.service;

import com.nationalchip.iot.cache.helper.KeyHelper;
import com.nationalchip.iot.data.model.auth.IUser;
import com.nationalchip.iot.data.model.auth.Status;
import com.nationalchip.iot.rest.model.auth.UserInfo;
import com.nationalchip.iot.security.authentication.AuthenticationDetails;
import com.nationalchip.iot.security.provider.IJwtProvider;
import com.nationalchip.iot.security.provider.JwtProvider;
import com.nationalchip.iot.tenancy.ITenantAware;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/25/18 4:45 PM
 * @Modified:
 */
@Service
public class AuthService {




    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IJwtProvider jwtProvider;



    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Autowired
    private ITenantAware tenantAware;




    public UserInfo login(String username, String password){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication = authenticationManager.authenticate(token);


        IUser user = (IUser) authentication.getPrincipal();
        Claims claims = new DefaultClaims();

        claims.put(JwtProvider.AUTHORTIES,user.getAuthorities());
        claims.put(JwtProvider.DISABLED,!user.isEnabled());


        Status status = user.getStatus();
        String jwt="";
        switch (status){
            case REGISTERED:
                jwt="";
                break;
            case ACTIVED:
                jwt = jwtProvider.generateToken(user.getUsername(),claims);
                break;
            case PENDING:
                jwt="";
                break;
            default:
                jwt = jwtProvider.generateToken(user.getUsername(),claims);
                break;

        }

        return new UserInfo(user.getUsername(),status,jwt);

    }

    public boolean logout(){
        String username = tenantAware.getCurrentTenant();

        Date expiration = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication !=null){
            if(authentication.getDetails() instanceof AuthenticationDetails){
                expiration = ((AuthenticationDetails)authentication.getDetails()).getExpiration();
            }
        }

        if(expiration!=null){
            redisTemplate.opsForValue().set(KeyHelper.tokenExpirationKey(username),true,expiration.getTime()-System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
        else{
            redisTemplate.opsForValue().set(KeyHelper.tokenExpirationKey(username),true);
        }
        return true;
    }

    public String refreshToken(String token){
        return null;
    }






}
