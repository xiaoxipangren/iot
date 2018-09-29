package com.nationalchip.iot.security.configuration;

import com.nationalchip.iot.security.authority.Authority;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/7/18 5:30 PM
 * @Modified:
 */
@ConfigurationProperties("iot.security")
public class SecurityProperty {

    private System system;

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public static class System {

        private static final String USER = Authority.SYSTEM;
        private static final String PASSWORD=Authority.SYSTEM;

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
                return USER;
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
