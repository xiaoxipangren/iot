package com.nationalchip.iot.security.authentication;

import com.nationalchip.iot.cache.redis.RedisService;
import com.nationalchip.iot.data.model.auth.IUser;
import com.nationalchip.iot.data.model.auth.Status;
import com.nationalchip.iot.security.jwt.ClaimsBuilder;
import com.nationalchip.iot.security.jwt.IClaimsBuilder;
import com.nationalchip.iot.security.jwt.IJwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/11/18 1:20 PM
 * @Modified:
 */
@Component
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisService redisService;

    @Autowired
    private IJwtProvider jwtProvider;

    @Override
    public Authentication authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication = authenticationManager.authenticate(token);

        IUser user = (IUser) authentication.getPrincipal();

        IClaimsBuilder builder = new ClaimsBuilder();
        builder.subject(user.getUsername())
                .authorities(user.getAuthorities().stream().map(a -> a.toString()).collect(Collectors.toList()));

        Status status = user.getStatus();
        String jwt="";
        switch (status){
            case REGISTERED:
                jwt="";
                break;
            case ACTIVED:
                jwt = jwtProvider.toToken(builder);
                break;
            case PENDING:
                jwt="";
                break;
            default:
                jwt = jwtProvider.toToken(builder);
                break;

        }


        return new UsernamePasswordAuthenticationToken(user,jwt);

    }

    @Override
    public void unAuthenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication !=null){
            String token = (String)authentication.getCredentials();
            Date expiration = null;
            if(authentication.getDetails() instanceof AuthenticationDetails){
                expiration = ((AuthenticationDetails)authentication.getDetails()).getExpiration();
            }

            if(expiration!=null){
                redisService.set(token,true,expiration.getTime()-System.currentTimeMillis(), TimeUnit.MILLISECONDS);
            }
            else{
                redisService.set(token,true);
            }
        }
    }

    @Override
    public <T> T runOnce(String principal, Supplier<T> supplier) {
        T t= supplier.get();
        unAuthenticate();
        return t;

    }
}
