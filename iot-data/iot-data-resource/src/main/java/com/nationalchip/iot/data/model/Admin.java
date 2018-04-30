package com.nationalchip.iot.data.model;

import com.nationalchip.iot.data.annotation.Comment;
import com.nationalchip.iot.data.model.auth.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 管理员类
 */

@Entity
@Table(name = "admin")
public class Admin extends User {

    public Admin(){

    }

    public Admin(String username,String password){
        super(username,password);
    }


    @Comment("是否限制Ip登录")
    @Column(name="restrict_ip")
    private boolean restrictIp;

    @Comment("是否限制Mac登录")
    @Column(name="restrict_mac")
    private boolean restrictMac;

    @Comment("是否限制Mac登录")
    @Column(name="ip")
    private String ip;

    @Comment("是否限制Mac登录")
    @Column(name="mac")
    private String mac;


    public boolean isRestrictIp() {
        return restrictIp;
    }

    public void setRestrictIp(boolean restrictIp) {
        this.restrictIp = restrictIp;
    }

    public boolean isRestrictMac() {
        return restrictMac;
    }

    public void setRestrictMac(boolean restrictMac) {
        this.restrictMac = restrictMac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }




}
