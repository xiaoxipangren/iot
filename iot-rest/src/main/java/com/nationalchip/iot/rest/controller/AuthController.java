package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.manager.IUserManager;
import com.nationalchip.iot.data.model.auth.*;
import com.nationalchip.iot.rest.exception.AuthException;
import com.nationalchip.iot.rest.resource.AuthAssembler;
import com.nationalchip.iot.rest.resource.AuthRequest;
import com.nationalchip.iot.rest.resource.AuthResource;
import com.nationalchip.iot.rest.resource.Response;
import com.nationalchip.iot.security.authentication.AccountTypeAuthenticationToken;
import com.nationalchip.iot.security.authentication.IAuthenticationService;
import com.nationalchip.iot.security.configuration.RestMappingConstant;
import com.nationalchip.iot.security.exception.AccountTypeNotMatchedException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = RestMappingConstant.REST_BASE_MAPPING+ RestMappingConstant.REST_AUTH_MAPPING)
@Api(tags = "鉴权API")
public class AuthController{

    private static final String CLIENT_HUB="hub";
    private static final String CLIENT_ADMIN="admin";
    private static final String CLIENT_APP="app";

    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private AuthAssembler authAssembler;

    @Autowired
    private IUserManager userManager;

    @PostMapping
    public ResponseEntity<Response> login(@RequestBody AuthRequest request){


        return authenticationService.runOnce(request.getUsername(),()->{
            try{

                String client = request.getClient();
                Class type = null;

                if(CLIENT_APP.equalsIgnoreCase(client))
                    type = Consumer.class;
                else if (CLIENT_ADMIN.equalsIgnoreCase(client))
                    type = Admin.class;
                else
                    type = Developer.class;

                AccountTypeAuthenticationToken token = new AccountTypeAuthenticationToken(request.getUsername(),request.getPassword(),type);

                Authentication authentication = authenticationService.authenticate(token);
                IUser user = (IUser) authentication.getPrincipal();

                User u = (User) user;
                if(u!=null){
                    u.setLastLogin(new Date(System.currentTimeMillis()));
                    userManager.update(u);
                }


                AuthResource authResource = authAssembler.toResource(user);
                authResource.setToken((String) authentication.getCredentials());

                return Response.created("登录成功",authResource);
            }
            catch (BadCredentialsException e){
                throw new AuthException("密码错误", HttpStatus.BAD_REQUEST);
            }
            catch (AccountTypeNotMatchedException e){
                throw new AuthException("禁止登录",HttpStatus.FORBIDDEN);
            }



        });

    }


    @DeleteMapping
    public ResponseEntity<Response> logout(){

        authenticationService.unAuthenticate();

        return Response.deleted("注销成功");
    }




}
