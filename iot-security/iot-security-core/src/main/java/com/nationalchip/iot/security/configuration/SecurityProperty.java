package com.nationalchip.iot.security.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/7/18 5:30 PM
 * @Modified:
 */
@ConfigurationProperties("iot.security")
public class SecurityProperty {

    private Admin admin;

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public static class Admin{

        private static final String ADMIN="admin";
        private static final String PASSWORD="admin";

        private String username;
        private String password;
        private String ip;
        private String mac;

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

        public String getUsername() {
            if (username==null || username.isEmpty())
                return ADMIN;
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {

            if(password==null || password.isEmpty())
                return PASSWORD;

            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
