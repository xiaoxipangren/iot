package com.nationalchip.iot.tenancy;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/25/18 3:50 PM
 * @Modified:
 */
public interface ITenantAware {
    String getCurrentTenant();

    <T> T runAs(final String tenant,TenantRunner<T> runner);
}
