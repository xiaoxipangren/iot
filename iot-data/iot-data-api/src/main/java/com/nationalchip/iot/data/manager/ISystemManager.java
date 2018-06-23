package com.nationalchip.iot.data.manager;


import java.util.function.Consumer;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/23/18 3:14 PM
 * @Modified:
 */
public interface ISystemManager {
    String currentTenant();
    void deleteTenant(String tenant);
    void forEachTenant(Consumer<String> consumer);
}
