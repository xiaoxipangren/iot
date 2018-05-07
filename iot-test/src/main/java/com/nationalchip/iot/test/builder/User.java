package com.nationalchip.iot.test.builder;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/3/18 11:02 AM
 * @Modified:
 */
public class User implements IUser {

    private String name;
    private String email;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
