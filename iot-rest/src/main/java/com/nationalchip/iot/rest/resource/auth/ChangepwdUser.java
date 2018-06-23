package com.nationalchip.iot.rest.resource.auth;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/8/18 2:47 PM
 * @Modified:
 */
public class ChangepwdUser {
    @NotNull
    @ApiModelProperty(value = "旧密码",required = true)
    private String oldPwd;

    @NotNull
    @ApiModelProperty(value = "新密码",required = true)
    private String newPwd;

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
