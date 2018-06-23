package com.nationalchip.iot.rest.resource.auth;

import com.nationalchip.iot.data.model.auth.Status;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/26/18 1:54 PM
 * @Modified:
 */

public class UserInfo {

    public UserInfo(){

    }


    public UserInfo(String username, String token) {
        this.token = token;
        this.username=username;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @ApiModelProperty("用于后续鉴权的Json Web Token")
    private String token;

    @ApiModelProperty("用户状态")
    private Status status;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机")
    private String phone;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

