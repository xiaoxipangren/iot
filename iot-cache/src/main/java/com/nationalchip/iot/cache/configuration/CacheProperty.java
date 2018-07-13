package com.nationalchip.iot.cache.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/3/18 8:25 PM
 * @Modified:
 */
@ConfigurationProperties("iot.cache")
public class CacheProperty {
    private long interval;

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }
}
