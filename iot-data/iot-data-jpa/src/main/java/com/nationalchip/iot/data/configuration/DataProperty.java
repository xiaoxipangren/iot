package com.nationalchip.iot.data.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 2:44 PM
 * @Modified:
 */

@ConfigurationProperties("iot.data")
public class DataProperty {

    private Fs fs;
    private Nginx nginx;

    public Nginx getNginx() {
        return nginx;
    }

    public void setNginx(Nginx nginx) {
        this.nginx = nginx;
    }

    public Fs getFs() {
        return fs;
    }

    public void setFs(Fs fs) {
        this.fs = fs;
    }

    public static class Fs{
        private String repo;

        private String statics;


        public String getRepo() {
            return repo;
        }

        public void setRepo(String repo) {
            this.repo = repo;
        }


        public String getStatics() {
            return statics;
        }

        public void setStatics(String statics) {
            this.statics = statics;
        }
    }

    public static class Nginx {
        private String server;
        private String location;

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
