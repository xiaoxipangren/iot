package com.nationalchip.iot.rest.model.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/26/18 5:20 PM
 * @Modified:
 */

@ApiModel
public class LoginUser {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "密码",required = true)
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
