package com.nationalchip.iot.rest.service;

import com.nationalchip.iot.cache.helper.KeyHelper;
import com.nationalchip.iot.data.model.auth.IUser;
import com.nationalchip.iot.data.model.auth.Status;
import com.nationalchip.iot.rest.exception.AuthException;
import com.nationalchip.iot.rest.resource.AuthAssembler;
import com.nationalchip.iot.rest.resource.AuthResource;
import com.nationalchip.iot.rest.resource.auth.UserInfo;
import com.nationalchip.iot.security.authentication.AuthenticationDetails;
import com.nationalchip.iot.security.provider.IJwtProvider;
import com.nationalchip.iot.security.provider.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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




    public AuthResource login(String username, String password){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication=null;
        try {
            authentication = authenticationManager.authenticate(token);
        }
        catch (BadCredentialsException exception){
            throw new AuthException("用户名或密码错误", HttpStatus.BAD_REQUEST);
        }

        /**
         * 这种方式无法解决多点登录问题
         * 一旦注销，将注销所有的token，
         * 而一旦登录，则所有token都可用
         */

        if(redisTemplate.hasKey(KeyHelper.tokenExpirationKey(username))){
            redisTemplate.delete(KeyHelper.tokenExpirationKey(username));
        }

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

        AuthAssembler authAssembler = new AuthAssembler();
        AuthResource authResource = authAssembler.toResource(user);

        authResource.setToken(jwt);
        return authResource;

    }

    public boolean logout(String token){

        Date expiration = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication !=null){
            if(authentication.getDetails() instanceof AuthenticationDetails){
                expiration = ((AuthenticationDetails)authentication.getDetails()).getExpiration();
            }
        }

        if(expiration!=null){
            redisTemplate.opsForValue().set(token,true,expiration.getTime()-System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
        else{
            redisTemplate.opsForValue().set(token,true);
        }
        return true;
    }

    public String refreshToken(String token){
        return null;
    }






}
