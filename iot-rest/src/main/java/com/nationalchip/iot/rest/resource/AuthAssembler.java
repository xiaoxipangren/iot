package com.nationalchip.iot.rest.resource;

import com.nationalchip.iot.data.model.auth.IUser;
import com.nationalchip.iot.rest.controller.AuthController;
import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/20/18 3:51 PM
 * @Modified:
 */
@Component
public class AuthAssembler extends IdentifiableResourceAssemblerSupport<IUser,AuthResource> {


    public AuthAssembler(){
        super(AuthController.class,AuthResource.class);
    }

    @Override
    public AuthResource toResource(IUser user) {

        AuthResource auth = new AuthResource();
        auth.setUsername(user.getUsername());
        auth.setAvatar(user.getAvatar());
        auth.setEmail(user.getEmail());
        auth.setStatus(user.getStatus());
        auth.setPhone(user.getPhone());

        return auth;
    }

}
