package com.nationalchip.iot.data.model.ota;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/8/18 9:35 AM
 * @Modified:
 */

/**
 * 设备更新状态枚举
 */
public enum Status {
    UNKNOWN,
    IN_SYNC,
    PENDING,
    ERROR,
    REGISTERED;
}
