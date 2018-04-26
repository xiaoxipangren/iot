package com.nationalchip.iot.rest.model.auth;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/9/18 9:35 AM
 * @Modified:
 */
public class UserLogin {
    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
