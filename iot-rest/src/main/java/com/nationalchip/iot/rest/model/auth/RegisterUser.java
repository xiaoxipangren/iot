package com.nationalchip.iot.rest.model.auth;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/26/18 8:48 PM
 * @Modified:
 */
public class RegisterUser {
    @ApiModelProperty(value = "用户名",required = true)
    @NotNull
    @Max(16)
    @Min(4)
    private String username;

    @ApiModelProperty(value = "密码",required = true)
    @NotNull
    @Min(8)
    @Max(16)
    private String password;

    @ApiModelProperty(value = "邮箱",required = true)
    @NotNull
    private String email;

    @ApiModelProperty("手机号")
    @Min(11)
    @Max(14)
    private String phone;

    @ApiModelProperty(value = "用户类型",allowableValues = "0(developer,default),1(administrator)")
    private int type;

    @ApiModelProperty(value="4位注册验证码",required = true)
    @NotNull
    @Min(4)
    @Max(4)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
