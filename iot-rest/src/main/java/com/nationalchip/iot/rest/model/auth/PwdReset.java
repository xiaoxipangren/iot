package com.nationalchip.iot.rest.model.auth;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/7/18 4:54 PM
 * @Modified:
 */
public class PwdReset {
    private String email;
    private String password;
    private String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
