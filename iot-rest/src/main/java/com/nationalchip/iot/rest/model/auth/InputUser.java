package com.nationalchip.iot.rest.model.auth;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/26/18 5:20 PM
 * @Modified:
 */
public class InputUser {
    private String username;
    private String email;
    private String phone;
    private String password;
    private int type;
    private String code;
    private String url;
    private String newPwd;
    private String setMailAction;

    public String getSetMailAction() {
        return setMailAction;
    }

    public void setSetMailAction(String setMailAction) {
        this.setMailAction = setMailAction;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
