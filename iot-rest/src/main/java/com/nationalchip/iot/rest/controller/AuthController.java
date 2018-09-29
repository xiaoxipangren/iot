package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.manager.IUserManager;
import com.nationalchip.iot.data.model.auth.IUser;
import com.nationalchip.iot.data.model.auth.User;
import com.nationalchip.iot.rest.exception.AuthException;
import com.nationalchip.iot.rest.resource.AuthAssembler;
import com.nationalchip.iot.rest.resource.AuthRequest;
import com.nationalchip.iot.rest.resource.AuthResource;
import com.nationalchip.iot.rest.resource.Response;
import com.nationalchip.iot.security.authentication.AccountTypeAuthenticationToken;
import com.nationalchip.iot.security.authentication.AccountTypeRequestClient;
import com.nationalchip.iot.security.authentication.IAuthenticationService;
import com.nationalchip.iot.security.authority.SecurityConstant;
import static com.nationalchip.iot.security.configuration.RestMapping.*;
import com.nationalchip.iot.security.exception.AccountTypeNotMatchedException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.nationalchip.iot.security.authority.AuthorityExpression.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(value = REST_BASE_MAPPING+ REST_AUTH_MAPPING)
@Api(tags = "鉴权API")
public class AuthController{

    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private AuthAssembler authAssembler;

    @Autowired
    private IUserManager userManager;



    @PostMapping
    @PreAuthorize(HAS_AUTH_LOGIN)
    public ResponseEntity<Response> login(@RequestBody AuthRequest request,HttpServletRequest httpServletRequest){


        return authenticationService.runOnce(request.getUsername(),()->{
            try{

                String ip = httpServletRequest.getRemoteAddr();


                String source = request.getClient()==null? SecurityConstant.CLIENT_HUB:request.getClient();
                AccountTypeRequestClient client = new AccountTypeRequestClient();
                client.setSource(source);
                client.setIp(ip);


                AccountTypeAuthenticationToken token = new AccountTypeAuthenticationToken(request.getUsername(),request.getPassword(),client);

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
