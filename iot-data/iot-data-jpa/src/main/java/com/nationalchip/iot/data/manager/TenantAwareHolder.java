package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.tenancy.ITenantAware;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/23/18 4:18 PM
 * @Modified:
 */
public final class TenantAwareHolder {

    private static final TenantAwareHolder INSTANCE = new TenantAwareHolder();

    @Autowired
    private ITenantAware tenantAware;

    private TenantAwareHolder() {
    }

    /**
     * @return the singleton {@link TenantAwareHolder} instance
     */
    public static TenantAwareHolder getInstance() {
        return INSTANCE;
    }

    /**
     * @return the {@link TenantAware} service
     */
    public ITenantAware getTenantAware() {
        return tenantAware;
    }
}
