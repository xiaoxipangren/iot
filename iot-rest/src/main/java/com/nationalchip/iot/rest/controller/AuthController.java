package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.model.User;
import com.nationalchip.iot.rest.model.UserInfo;
import com.nationalchip.iot.rest.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {


    @Autowired
    private AuthService authService;


    @RequestMapping(value = "/signin",method= RequestMethod.POST)
    public ResponseEntity<UserInfo> login(@RequestParam String username, @RequestParam String password){

        UserInfo user = authService.login(username,password);

        return new ResponseEntity<UserInfo>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/signup",method= RequestMethod.POST,consumes="application/json",produces="application/json;charset=UTF-8")
    public ResponseEntity<Boolean> register(@RequestBody User user){

        boolean result = authService.register(user);

        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

}
