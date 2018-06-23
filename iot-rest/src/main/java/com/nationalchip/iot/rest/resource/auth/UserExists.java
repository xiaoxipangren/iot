package com.nationalchip.iot.rest.resource.auth;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/18/18 1:40 PM
 * @Modified:
 */
public class UserExists {
    @ApiModelProperty("用户是否存在")
    private boolean existed;

    public boolean isExisted() {
        return existed;
    }

    public void setExisted(boolean existed) {
        this.existed = existed;
    }

    public UserExists(boolean existed){
        this.existed=existed;
    }
}
