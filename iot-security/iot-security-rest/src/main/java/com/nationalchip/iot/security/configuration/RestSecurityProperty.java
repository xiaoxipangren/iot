package com.nationalchip.iot.security.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/26/18 9:12 AM
 * @Modified:
 */
@ConfigurationProperties("iot.security")
 public class RestSecurityProperty {
    public Jwt getJwt() {
        return jwt;
    }

    public void setJwt(Jwt jwt) {
        this.jwt = jwt;
    }

    private Jwt jwt;
    private Builtin builtin;

    public Builtin getBuiltin() {
        return builtin;
    }

    public void setBuiltin(Builtin builtin) {
        this.builtin = builtin;
    }

    public static class Jwt{

        private String alg;

        private String secret;

        private String issuer;

        private String expiration;
        private String prefix;

        private String header;

        public String getAlg() {
            return alg;
        }

        public void setAlg(String alg) {
            this.alg = alg;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getIssuer() {
            return issuer;
        }

        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }

        public String getExpiration() {
            return expiration;
        }

        public void setExpiration(String expiration) {
            this.expiration = expiration;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }
    }


    public static class Builtin{
        private String user;
        private String role;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }


}
