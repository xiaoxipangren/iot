package com.nationalchip.iot.tenancy;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/15/18 10:44 AM
 * @Modified:
 */
@FunctionalInterface
public interface TenantRunner<T> {
    T run();
}