package com.nationalchip.iot.rest.model.auth;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/18/18 1:40 PM
 * @Modified:
 */
public class UserExists {
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
