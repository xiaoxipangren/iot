package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.rest.resource.AuthResource;
import com.nationalchip.iot.rest.resource.Response;
import com.nationalchip.iot.rest.resource.auth.UserInfo;
import com.nationalchip.iot.rest.service.AuthService;
import com.nationalchip.iot.security.configuration.RestConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = RestConstant.REST_BASE_MAPPING+RestConstant.REST_AUTH_MAPPING)
@Api(tags = "鉴权API")
public class AuthController{

    @Autowired
    private AuthService authService;


    @ApiOperation(value = "登录",notes = "根据用户名和密码登录，该API会返回用于鉴权的Json Web Token，请妥善保存，某些受保护的API需要使用Authorization请求头携带该token",
            response = UserInfo.class)
    @ApiImplicitParam(name="loginUser",value = "登录信息",paramType = "body",
            dataType = "LoginUser",required = true)
    @PostMapping
    public ResponseEntity<Response> login(@RequestParam("username")final String username, @RequestParam("password")final String password){

        AuthResource authResource = authService.login(username,password);

        return Response.created("登录成功",authResource);
    }


    @ApiOperation(value = "注销登录",notes = "注销登录，将颁发的token失效，注意该API是受保护的，需要提供token，该token将被标记为失效")
    @ApiImplicitParams({@ApiImplicitParam(name="Authorization",value = "认证头",paramType = "header",
            dataType = "String",required = true),@ApiImplicitParam(name="Authorization",value = "认证头",paramType = "header",
            dataType = "String",required = true)})
    @DeleteMapping
    public ResponseEntity<Response> logout(@RequestHeader(RestConstant.REST_JWT_HEADER) String token){

        token=token.replace(RestConstant.REST_JWT_PREFIX,"");

        authService.logout(token);

        return Response.deleted("注销成功");
    }

}
