package com.nationalchip.iot.data.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 2:44 PM
 * @Modified:
 */

@ConfigurationProperties("iot.data")
public class DataProperties {

    private Fs fs;

    public Fs getFs() {
        return fs;
    }

    public void setFs(Fs fs) {
        this.fs = fs;
    }

    public class Fs{
        private String repo;

        public String getRepo() {
            return repo;
        }

        public void setRepo(String repo) {
            this.repo = repo;
        }
    }
}
