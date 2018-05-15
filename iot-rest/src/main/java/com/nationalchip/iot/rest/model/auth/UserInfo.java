package com.nationalchip.iot.rest.model.auth;

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


    public UserInfo(String username,Status status, String token) {
        this.user = new User(username,status);
        this.token = token;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ApiModelProperty("用于后续鉴权的Json Web Token")
    private String token;

    public static class User{

        public User(){

        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @ApiModelProperty("用户名")
        private String username;
        public User(String username,Status status){
            this.username=username;
            this.status=status;
        }

        @ApiModelProperty("用户状态")
        private Status status;

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }
    }

}

