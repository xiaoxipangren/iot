package com.nationalchip.iot.rest.model.auth;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/7/18 4:54 PM
 * @Modified:
 */
public class PwdReset {
    @ApiModelProperty(value = "账户绑定的邮箱",required = true)
    @NotNull
    private String email;

    @ApiModelProperty(value = "新密码",required = true)
    @NotNull
    private String password;

    @ApiModelProperty(value = "验证码",required = true)
    @NotNull
    @Max(4)
    @Min(4)
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
