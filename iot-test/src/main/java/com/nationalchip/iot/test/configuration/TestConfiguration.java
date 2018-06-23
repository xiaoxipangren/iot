package com.nationalchip.iot.test.configuration;

import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/19/18 1:34 PM
 * @Modified:
 */

@Configuration
public class TestConfiguration {

    @LocalServerPort
    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
