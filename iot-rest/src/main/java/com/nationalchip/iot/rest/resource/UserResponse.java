package com.nationalchip.iot.rest.resource;

import com.nationalchip.iot.data.model.auth.Status;

import java.util.Date;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/29/18 10:07 AM
 * @Modified:
 */
public class UserResponse extends  NamedResponse{

    private Date lastLogin;
    private String phone;
    private String email;
    private Status status;
    private String avatar;

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
