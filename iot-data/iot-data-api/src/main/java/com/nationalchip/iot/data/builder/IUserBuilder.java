package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.auth.IUser;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/3/18 12:54 PM
 * @Modified:
 */
public interface IUserBuilder extends INamedBuilder<IUser> {
    IUserBuilder phone(String phone);
    IUserBuilder email(String email);
    IUserBuilder type(int type);
    IUserBuilder password(String password);
    IUserBuilder avatar(String avatar);
}
