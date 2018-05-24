package com.nationalchip.iot.data.manager;

import com.sun.istack.internal.NotNull;

import java.util.function.Consumer;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/23/18 3:14 PM
 * @Modified:
 */
public interface ISystemManager {
    String currentTenant();
    void deleteTenant(@NotNull String tenant);
    void forEachTenant(Consumer<String> consumer);
}
