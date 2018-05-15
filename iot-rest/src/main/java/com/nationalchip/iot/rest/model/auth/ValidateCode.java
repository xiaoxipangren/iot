package com.nationalchip.iot.rest.model.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/8/18 1:16 PM
 * @Modified:
 */
@ApiModel(description = "待验证的验证码数据封装实体")
public class ValidateCode extends SendMail {

    @NotNull
    @ApiModelProperty(value = "待验证的验证码值",required = true)
    private String code;


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }
}
