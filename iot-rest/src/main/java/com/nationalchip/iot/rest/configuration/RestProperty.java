package com.nationalchip.iot.rest.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/20/18 4:59 PM
 * @Modified:
 */

@ConfigurationProperties("iot.rest")
@PropertySource("classpath:iot-rest-default.properties")
public class RestProperty {

}
