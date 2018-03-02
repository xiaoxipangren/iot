package com.nationalchip.iot.rest.service;

import com.nationalchip.iot.data.manager.UserManager;
import com.nationalchip.iot.data.model.User;
import com.nationalchip.iot.rest.model.UserInfo;
import com.nationalchip.iot.security.provider.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private UserManager userManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserInfo login(String username, String password){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication = authenticationManager.authenticate(token);

        //auth登录后保存登录状态没有任何意义，只需返回token即可
        //SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);

        User user = (User) authentication.getPrincipal();

        return new UserInfo(user.getUsername(),jwt);


    }


    public boolean register(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userManager.createUser(user);
            return true;
        }
        catch (Exception e){
            return false;
        }

    }


    public String refresh(String token){
        return null;
    }

}
