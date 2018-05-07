package com.nationalchip.iot.rest.model.auth;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/7/18 3:42 PM
 * @Modified:
 */
public class UserEmail {
    private String email;
    private String username;
    private String action;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
