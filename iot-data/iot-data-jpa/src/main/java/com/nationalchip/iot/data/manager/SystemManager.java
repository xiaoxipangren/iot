package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.auth.IUser;
import com.nationalchip.iot.data.provider.ITenantCacheKeyGenerator;
import com.nationalchip.iot.data.provider.SystemTenantCacheKeyGenerator;
import com.nationalchip.iot.data.repository.UserRepository;
import com.nationalchip.iot.tenancy.ITenantAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.function.Consumer;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/23/18 3:16 PM
 * @Modified:
 */

@Transactional(readOnly = true)
@Validated
public class SystemManager implements ISystemManager,ITenantCacheKeyGenerator {

    @Autowired
    private ITenantAware tenantAware;

    @Autowired
    private SystemTenantCacheKeyGenerator tenantCacheKeyGenerator;

    @Autowired
    private UserRepository userRepository;


    @Override
    @Cacheable(value = "currentTenant", keyGenerator = "currentTenantKeyGenerator", unless = "#result == null")
    public String currentTenant() {
        final String initialTenantCreation = tenantCacheKeyGenerator.getCreateInitialTenant().get();
        if (initialTenantCreation == null) {
            final IUser user = userRepository
                    .findByName(tenantAware.getCurrentTenant());
            return user != null ? user.getName() : null;
        }
        return initialTenantCreation;
    }

    @Override
    public void deleteTenant(String tenant) {

    }

    @Override
    public void forEachTenant(Consumer<String> consumer) {

    }

    @Override
    public KeyGenerator currentTenantKeyGenerator() {
        return tenantCacheKeyGenerator.currentTenantKeyGenerator();
    }
}
