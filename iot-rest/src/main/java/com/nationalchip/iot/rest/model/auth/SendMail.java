package com.nationalchip.iot.rest.model.auth;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/8/18 1:28 PM
 * @Modified:
 */
public class SendMail {
    @ApiModelProperty(value = "验证码接收邮箱",required = true)
    @NotNull
    private String email;

    @ApiModelProperty(value = "验证码类型，可选validate/resetpwd，前者用于注册验证码，后者重置密码验证码",required = true,
            allowableValues = "validate,resetpwd")
    @NotNull
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
