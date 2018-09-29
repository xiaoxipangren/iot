package com.nationalchip.iot.data.model;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/8/18 1:46 PM
 * @Modified:
 */


/**
 * 具有版本号标示的资源的父接口
 */
public interface IVersionedEntity extends IArchivedEntity {
    String getVersion();
}
