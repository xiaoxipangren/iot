package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.builder.IUserBuilder;
import com.nationalchip.iot.data.builder.UserBuilder;
import com.nationalchip.iot.data.manager.UserManager;
import com.nationalchip.iot.data.model.auth.IUser;
import com.nationalchip.iot.rest.exception.RestException;
import com.nationalchip.iot.rest.resource.Response;
import com.nationalchip.iot.rest.resource.UserRequest;
import com.nationalchip.iot.rest.resource.UserResponse;
import com.nationalchip.iot.security.authentication.IAuthenticationService;
import com.nationalchip.iot.security.authority.AuthorityExpression;
import static com.nationalchip.iot.security.configuration.RestMapping.*;
import com.nationalchip.iot.tenancy.ITenantAware;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.nationalchip.iot.security.authority.AuthorityExpression.*;
import static com.nationalchip.iot.security.authority.Authority.*;
/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/22/18 8:49 AM
 * @Modified:
 */

@RestController
@RequestMapping(value = REST_BASE_MAPPING+ REST_USER_MAPPING)
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
    public ResponseEntity<Response> create(@RequestBody UserRequest request){

        if(request.isRegister()) {//禁止管理员用户注册
            if (request.getType() == TYPE_ADMIN)
                throw new RestException("权限不足，禁止访问", HttpStatus.FORBIDDEN);

            return register(request);
        }


        return createOther(request);
    }


    /**
     * 后台管理系统只能通过超级管理员进行新用户创建
     * @param request
     * @return
     */
    @PreAuthorize(HAS_ROLE_SYSTEM + OR + HAS_AUTH_CREATE_USER)
    private ResponseEntity<Response> createOther(UserRequest request){
        return super.create(request);
    }


    @RequestMapping(method= RequestMethod.POST,value = "avatar")
    @PreAuthorize(HAS_AUTH_UPDATE_USER)
    public ResponseEntity<Response> avatar(@RequestParam(value = "avatar")MultipartFile avatar){
        try {
            String url = getManager().changeAvatar(avatar.getInputStream());
            return Response.ok("头像修改成功",(Object) url);
        } catch (IOException e) {
            throw new RestException("修改头像发生IO错误");
        }
    }

    @PreAuthorize(AuthorityExpression.HAS_AUTH_REGISTER)
    private ResponseEntity<Response> register(@RequestBody UserRequest request){
        return authenticationService.runOnce(request.getEmail().get(),()-> super.create(request));
    }


    @Override
    @RequestMapping(value = REST_ID_MAPPING,method= {RequestMethod.PATCH,RequestMethod.POST})
    public ResponseEntity<Response> update(@PathVariable final Long id,@RequestBody UserRequest request) {
        if(id<=0){
            return updateSelf(request);
        }

        return updateOther(id, request);
    }

    /**
     * TODO:
     * 测试能否通过后台管理系统直接重置用户密码
     * @param id
     * @param request
     * @return
     */
    @PreAuthorize(HAS_ROLE_SYSTEM)
    private ResponseEntity<Response> updateOther(Long id,UserRequest request){
        return super.update(id,request);
    }



    private ResponseEntity<Response> updateSelf(UserRequest request) {

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
            return updateSelf(request,tenantAware.getCurrentTenant());
        }
    }


    @PreAuthorize(HAS_AUTH_UPDATE_USER)
    private ResponseEntity<Response> updateSelf(UserRequest request, String currentUser){
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


    @PreAuthorize(HAS_AUTH_UPDATE_USER)
    private ResponseEntity<Response> changePassword(final String oldpwd,final String password){
        getManager().changePassword(oldpwd,password);
        return Response.ok("密码修改成功",true);
    }


    @Override
    @RequestMapping(method = RequestMethod.DELETE,value = REST_ID_MAPPING)
    @PreAuthorize(HAS_AUTH_DELETE_USER)
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Override
    public UserManager getManager() {
        return (UserManager)super.getManager();
    }
}
