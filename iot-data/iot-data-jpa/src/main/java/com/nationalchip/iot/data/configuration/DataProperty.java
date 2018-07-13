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

    public Fs getFs() {
        return fs;
    }

    public void setFs(Fs fs) {
        this.fs = fs;
    }

    public static class Fs{
        private String repo;

        private String avatar;

        private String statics;

        private String image;

        private String news;

        private String captcha;

        public String getCaptcha() {
            return captcha;
        }

        public void setCaptcha(String captcha) {
            this.captcha = captcha;
        }

        public String getNews() {
            return news;
        }

        public void setNews(String news) {
            this.news = news;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getRepo() {
            return repo;
        }

        public void setRepo(String repo) {
            this.repo = repo;
        }




        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getStatics() {
            return statics;
        }

        public void setStatics(String statics) {
            this.statics = statics;
        }
    }
}
