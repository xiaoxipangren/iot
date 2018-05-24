package com.nationalchip.iot.data.model;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/8/18 1:46 PM
 * @Modified:
 */
public interface IVersionedEntity extends IArchivedEntity {
    String getVersion();
}
