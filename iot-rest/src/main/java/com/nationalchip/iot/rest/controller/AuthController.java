package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.rest.model.RestResult;
import com.nationalchip.iot.rest.model.auth.LoginUser;
import com.nationalchip.iot.rest.model.auth.UserInfo;
import com.nationalchip.iot.rest.service.AuthService;
import com.nationalchip.iot.security.configuration.RestConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = RestConstant.REST_BASE_MAPPING+RestConstant.REST_AUTH_MAPPING)
@Api(tags = "鉴权API")
public class AuthController extends BaseController{

    @Autowired
    private AuthService authService;


    @ApiOperation(value = "登录",notes = "根据用户名和密码登录，该API会返回用于鉴权的Json Web Token，请妥善保存，某些受保护的API需要使用Authorization请求头携带该token",
            response = UserInfo.class)
    @ApiImplicitParam(name="loginUser",value = "登录信息",paramType = "body",
            dataType = "LoginUser",required = true)
    @RequestMapping(value = RestConstant.REST_LOGIN_ACTION,method= RequestMethod.POST)
    public ResponseEntity<RestResult> login(@RequestBody LoginUser loginUser){

        UserInfo user = authService.login(loginUser.getUsername(),loginUser.getPassword());

        return ok("登录成功",user);
    }


    @ApiOperation(value = "注销登录",notes = "注销登录，将颁发的token失效，注意该API是受保护的，需要提供token，该token将被标记为失效")
    @ApiImplicitParams({@ApiImplicitParam(name="Authorization",value = "认证头",paramType = "header",
            dataType = "String",required = true),@ApiImplicitParam(name="Authorization",value = "认证头",paramType = "header",
            dataType = "String",required = true)})
    @RequestMapping(value = "/logout",method = {RequestMethod.POST ,RequestMethod.GET})
    public ResponseEntity<RestResult> logout(@RequestHeader(RestConstant.REST_JWT_HEADER) String token){

        token=token.replace(RestConstant.REST_JWT_PREFIX,"");

        boolean result = authService.logout(token);

        return ok(result);
    }

}
