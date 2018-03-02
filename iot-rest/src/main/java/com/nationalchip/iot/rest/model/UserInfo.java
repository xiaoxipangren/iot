package com.nationalchip.iot.rest.model;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/26/18 1:54 PM
 * @Modified:
 */
public class UserInfo {

    public UserInfo(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String username;
    private String token;
}
