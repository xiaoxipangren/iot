package com.nationalchip.iot.message.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/26/18 11:00 AM
 * @Modified:
 */

@Configuration
@ComponentScan(basePackages = "com.nationalchip.iot.message")
@PropertySource("classpath:iot-message-default.properties")
public class MessageConfiguration {



}
