package com.nationalchip.iot.data.provider;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/23/18 3:32 PM
 * @Modified:
 */

@FunctionalInterface
public interface ITenantCacheKeyGenerator {
    @Bean
    KeyGenerator currentTenantKeyGenerator();
}
