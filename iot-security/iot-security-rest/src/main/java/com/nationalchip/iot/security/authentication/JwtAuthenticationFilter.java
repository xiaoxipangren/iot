package com.nationalchip.iot.security.authentication;

import com.nationalchip.iot.cache.helper.KeyHelper;
import com.nationalchip.iot.data.model.auth.IAuthority;
import com.nationalchip.iot.security.configuration.RestConstant;
import com.nationalchip.iot.security.provider.IJwtProvider;
import io.jsonwebtoken.Claims;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Set;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public IJwtProvider getJwtProvider() {
        return jwtProvider;
    }

    public void setJwtProvider(IJwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    private IJwtProvider jwtProvider;



    private RedisTemplate<String,Object> redisTemplate;

    public JwtAuthenticationFilter(IJwtProvider jwtProvider,RedisTemplate<String,Object> redisTemplate){
        this.jwtProvider=jwtProvider;
        this.redisTemplate = redisTemplate;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(RestConstant.REST_JWT_HEADER);
        String prefix = RestConstant.REST_JWT_PREFIX;
        if(header!=null && header.startsWith(prefix)){
            String token=header.replace(prefix,"");
            Claims claims = jwtProvider.parseToken(token);
            Authentication authentication = restoreAuthentication(claims);

            if(authentication != null) {
                Object logouted = redisTemplate.opsForValue().get(KeyHelper.tokenExpirationKey(authentication.getName()));

                if (logouted instanceof Boolean && (boolean) logouted) {

                    PrintWriter writer = response.getWriter();
                    writer.print("token已过期，请重新登录");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    return;
                } else {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private Authentication restoreAuthentication(Claims claims){
        String username = claims.getSubject();
        Set<IAuthority> authorities = (Set<IAuthority>) claims.get(IJwtProvider.AUTHORTIES);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,null,authorities);

        Date expiration = claims.getExpiration();
        AuthenticationDetails details = new AuthenticationDetails(username,null,expiration);
        authentication.setDetails(details);
        return authentication;
    }

}
