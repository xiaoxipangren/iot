package com.nationalchip.iot.security.http;

import com.nationalchip.iot.security.configuration.SecurityProperty;
import com.nationalchip.iot.security.provider.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {


    public void setSecurityProperty(SecurityProperty securityProperty) {
        this.securityProperty = securityProperty;
    }

    public SecurityProperty getSecurityProperty() {
        return securityProperty;
    }

    public JwtProvider getJwtProvider() {
        return jwtProvider;
    }

    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Autowired
    private SecurityProperty securityProperty;

    @Autowired
    private JwtProvider jwtProvider;


    public JwtAuthenticationFilter(JwtProvider jwtProvider,SecurityProperty securityProperty){
        this.jwtProvider=jwtProvider;
        this.securityProperty=securityProperty;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(securityProperty.getHeader());
        String prefix = securityProperty.getPrefix().trim()+" ";
        if(header.startsWith(prefix)){
            String token=header.replace(prefix,"");
            Authentication authentication = jwtProvider.parseToken(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
