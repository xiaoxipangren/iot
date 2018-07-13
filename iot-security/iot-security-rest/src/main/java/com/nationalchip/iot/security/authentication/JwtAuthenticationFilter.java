package com.nationalchip.iot.security.authentication;

import com.nationalchip.iot.cache.redis.IRedisService;
import com.nationalchip.iot.security.configuration.RestSecurityProperty;
import com.nationalchip.iot.security.exception.JwtDisabledException;
import com.nationalchip.iot.security.exception.JwtExpiratedException;
import com.nationalchip.iot.security.jwt.IJwtProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public IJwtProvider getJwtProvider() {
        return jwtProvider;
    }

    public void setJwtProvider(IJwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    private IJwtProvider jwtProvider;

    private RestSecurityProperty securityProperty;

    private IRedisService redisService;

    public JwtAuthenticationFilter(IJwtProvider jwtProvider, IRedisService redisService, RestSecurityProperty securityProperty){
        this.jwtProvider=jwtProvider;
        this.redisService = redisService;
        this.securityProperty=securityProperty;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(securityProperty.getJwt().getHeader());// RestMappingConstant.REST_JWT_HEADER)
        String prefix = securityProperty.getJwt().getPrefix(); //RestMappingConstant.REST_JWT_PREFIX;
        if(header!=null && header.startsWith(prefix)){
            String token=header.replace(prefix,"").trim();
            if(redisService.hasKey(token)){
                Object logouted = redisService.get(token);
                if (logouted instanceof Boolean && (boolean) logouted) {

                    throw new BadCredentialsException("token已失效");
                }
            }
            else {
                try{
                    Authentication authentication = jwtProvider.fromToken(token);

                    if(authentication != null) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }catch (JwtExpiratedException | JwtDisabledException e){
                    throw new BadCredentialsException("token已过期");
                }

            }


        }

        filterChain.doFilter(request, response);
    }


}
