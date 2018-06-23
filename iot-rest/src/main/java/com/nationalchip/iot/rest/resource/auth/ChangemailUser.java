package com.nationalchip.iot.rest.resource.auth;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/8/18 2:48 PM
 * @Modified:
 */
public class ChangemailUser {
    @NotNull
    @ApiModelProperty(value = "绑定的新邮箱",required = true)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
