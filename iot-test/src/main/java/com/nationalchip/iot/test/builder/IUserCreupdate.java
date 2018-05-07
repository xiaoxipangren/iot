package com.nationalchip.iot.test.builder;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/3/18 1:13 PM
 * @Modified:
 */
public interface IUserCreupdate extends ICreupdate<IUser> {
    IUserCreupdate name(String name);
    IUserCreupdate phone(String phone);
    IUserCreupdate email(String email);
}
