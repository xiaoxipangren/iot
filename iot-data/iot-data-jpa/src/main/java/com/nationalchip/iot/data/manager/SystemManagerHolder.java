package com.nationalchip.iot.data.manager;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/23/18 3:54 PM
 * @Modified:
 */
public final class SystemManagerHolder {

    private static final SystemManagerHolder INSTANCE = new SystemManagerHolder();

    @Autowired
    private ISystemManager systemManager;

    private SystemManagerHolder() {
    }


    public static SystemManagerHolder getInstance() {
        return INSTANCE;
    }


    public ISystemManager getSystemManagement() {
        return systemManager;
    }


    public void setSystemManagement(final ISystemManager systemManager) {
        this.systemManager = systemManager;
    }


    public String currentTenant() {
        return systemManager.currentTenant();
    }

}
