package com.nationalchip.iot.security.authentication;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/17/18 11:11 AM
 * @Modified:
 */
public class AccountTypeRequestClient {
    private String source;
    private String ip;
    private String mac;


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
