package com.nationalchip.iot.data.model.auth;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/17/18 10:34 AM
 * @Modified:
 */
public interface IAdmin extends IUser {
    boolean isRestrictIp();
    boolean isRestrictMac();
    String getIp();
    String getMac();
}
