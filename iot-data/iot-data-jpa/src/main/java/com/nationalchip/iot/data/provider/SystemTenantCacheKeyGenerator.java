package com.nationalchip.iot.data.provider;

import com.nationalchip.iot.tenancy.ITenantAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/23/18 3:36 PM
 * @Modified:
 */

@Service
public class SystemTenantCacheKeyGenerator implements ITenantCacheKeyGenerator {
    @Autowired
    private ITenantAware tenantAware;
    private final ThreadLocal<String> createInitialTenant = new ThreadLocal<>();

    public class CurrentTenantKeyGenerator implements KeyGenerator {
        @Override
        public Object generate(final Object target, final Method method, final Object... params) {
            final String initialTenantCreation = createInitialTenant.get();
            if (initialTenantCreation == null) {
                return SimpleKeyGenerator.generateKey(tenantAware.getCurrentTenant().toUpperCase(),
                        tenantAware.getCurrentTenant().toUpperCase());
            }
            return SimpleKeyGenerator.generateKey(initialTenantCreation.toUpperCase(),
                    initialTenantCreation.toUpperCase());
        }
    }

    @Bean
    @Override
    public KeyGenerator currentTenantKeyGenerator() {
        return new CurrentTenantKeyGenerator();
    }

    public ThreadLocal<String> getCreateInitialTenant() {
        return createInitialTenant;
    }
}
