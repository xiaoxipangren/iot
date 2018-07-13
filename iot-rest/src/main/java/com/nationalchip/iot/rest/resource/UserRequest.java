package com.nationalchip.iot.rest.resource;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/29/18 10:24 AM
 * @Modified:
 */
public class UserRequest extends NamedRequest {
    private String phone;
    private String email;
    private String avatar;
    private String password;
    private String username;
    private String oldpwd;
    private boolean register=false;

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public Optional<String> getOldpwd() {
        return Optional.ofNullable(oldpwd);
    }

    public void setOldpwd(String oldpwd) {
        this.oldpwd = oldpwd;
    }

    public Optional<String> getUsername() {
        return Optional.ofNullable(username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Optional<String> getPhone() {
        return Optional.ofNullable(phone);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Optional<String> getAvatar() {
        return Optional.ofNullable(avatar);
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
