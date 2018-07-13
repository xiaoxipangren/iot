package com.nationalchip.iot.message.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/3/18 8:19 PM
 * @Modified:
 */

@ConfigurationProperties("iot.message")
public class MessageProperty {
    private Captcha captcha;

    public Captcha getCaptcha() {
        return captcha;
    }

    public void setCaptcha(Captcha captcha) {
        this.captcha = captcha;
    }

    public static class Captcha{
        private long interval;
        private long expiration;

        public long getInterval() {
            return interval;
        }

        public void setInterval(long interval) {
            this.interval = interval;
        }

        public long getExpiration() {
            return expiration;
        }

        public void setExpiration(long expiration) {
            this.expiration = expiration;
        }
    }

}
