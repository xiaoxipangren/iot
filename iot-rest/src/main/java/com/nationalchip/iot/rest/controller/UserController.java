package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.builder.IUserBuilder;
import com.nationalchip.iot.data.builder.UserBuilder;
import com.nationalchip.iot.data.manager.UserManager;
import com.nationalchip.iot.data.model.auth.IUser;
import com.nationalchip.iot.rest.resource.Response;
import com.nationalchip.iot.rest.resource.UserRequest;
import com.nationalchip.iot.rest.resource.UserResponse;
import com.nationalchip.iot.security.authentication.IAuthenticationService;
import com.nationalchip.iot.security.authority.AuthorityExpression;
import com.nationalchip.iot.security.configuration.RestMappingConstant;
import com.nationalchip.iot.tenancy.ITenantAware;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.nationalchip.iot.security.authority.AuthorityExpression.*;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/22/18 8:49 AM
 * @Modified:
 */

@RestController
@RequestMapping(value = RestMappingConstant.REST_BASE_MAPPING+ RestMappingConstant.REST_USER_MAPPING)
@Api(tags = "用户API")
public class UserController extends BaseController<IUser,UserResponse,IUserBuilder,UserRequest>{

    @Autowired
    private ITenantAware tenantAware;

    @Autowired
    private IAuthenticationService authenticationService;

    @Override
    @ApiOperation(value ="注册新用户",notes = "根据输入的用户名、邮箱和密码注册新用户")
    @ApiImplicitParam(name = "registerUser",value = "注册用户",paramType = "body",dataType = "RegisterUser",
            required = true,examples=@Example(value = {
            @ExampleProperty(mediaType = "application/json",value = "{\"username\":\"username\"}")
    }))
    @RequestMapping(method= RequestMethod.POST,consumes="application/json",produces="application/json;charset=UTF-8")
    @PreAuthorize(HAS_AUTH_REGISTER+HAS_AUTH_OR+HAS_AUTH_CREATE_USER)
    public ResponseEntity<Response> create(@RequestBody UserRequest request){

        if(request.isRegister())
            return register(request);

        return super.create(request);
    }


    @PreAuthorize(AuthorityExpression.HAS_AUTH_REGISTER)
    private ResponseEntity<Response> register(@RequestBody UserRequest request){
        return authenticationService.runOnce(request.getEmail().get(),()-> super.create(request));
    }


    @Override
    @RequestMapping(value = RestMappingConstant.REST_ID_MAPPING,method= RequestMethod.PATCH)
    public ResponseEntity<Response> update(@PathVariable Long id,@RequestBody UserRequest request) {
        if(id<=0){
            return update(request);
        }

        return super.update(id, request);
    }



    private ResponseEntity<Response> update(UserRequest request) {

        //密码相关，需要特殊处理
        if(request.getPassword().isPresent()){

            if(!request.getOldpwd().isPresent()){
                return resetPassword(request.getEmail().get(),request.getPassword().get());
            }
            else{
                return changePassword(request.getOldpwd().get(),request.getPassword().get());
            }
        }//非密码相关，标准流程
        else{
            return update(request,tenantAware.getCurrentTenant());
        }
    }



    private ResponseEntity<Response> update(UserRequest request,String currentUser){
        IUserBuilder builder = getAssembler().fromRequest(request);
        builder.name(currentUser);

        IUser user = getManager().update(builder);

        return Response.ok("修改用户信息成功",getAssembler().toResource(user));
    }


    @PreAuthorize(AuthorityExpression.HAS_AUTH_RESET_PASSWORD)
    private ResponseEntity<Response> resetPassword(final String email, final String password){

        return authenticationService.runOnce(email,() ->{
            IUserBuilder builder = new UserBuilder();
            builder.email(email);
            builder.password(password);

            getManager().update(builder);

            return Response.ok("密码重置成功",true);
        });
    }

    private ResponseEntity<Response> changePassword(final String oldpwd,final String password){
        ((UserManager)getManager()).changePassword(oldpwd,password);
        return Response.ok("密码修改成功",true);
    }



}
