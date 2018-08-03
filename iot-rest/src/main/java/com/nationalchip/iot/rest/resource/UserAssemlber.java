package com.nationalchip.iot.rest.resource;

import com.nationalchip.iot.data.builder.IUserBuilder;
import com.nationalchip.iot.data.model.auth.IUser;
import com.nationalchip.iot.rest.controller.UserController;
import com.nationalchip.iot.rest.exception.RestException;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/29/18 5:00 PM
 * @Modified:
 */

@Component
public class UserAssemlber extends NamedAssembler<IUser,UserResponse,IUserBuilder,UserRequest> {
    public UserAssemlber() {
        super(UserController.class, UserResponse.class);
    }


    @Override
    public UserResponse toResource(IUser entity) {
        UserResponse response = super.toResource(entity);
        response.setAvatar(entity.getAvatar());
        response.setLastLogin(entity.getLastLogin());
        response.setPhone(entity.getPhone());
        response.setEmail(entity.getEmail());

        return response;
    }

    @Override
    public IUserBuilder fromRequest(UserRequest request) {
        IUserBuilder builder = super.fromRequest(request);
        request.getEmail().ifPresent(e -> builder.email(e));
        request.getPassword().ifPresent(p -> builder.password(p));
        request.getPhone().ifPresent(p -> builder.phone(p));
        request.getAvatar().ifPresent(a -> builder.avatar(a));
        request.getUsername().ifPresent(n ->builder.name(n));

        return builder;
    }

    @Override
    protected IUserBuilder builder() {
        return getBuilderFactory().user();
    }
}
