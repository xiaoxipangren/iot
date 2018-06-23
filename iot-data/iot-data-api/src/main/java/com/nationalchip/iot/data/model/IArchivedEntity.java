package com.nationalchip.iot.data.model;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/8/18 12:59 PM
 * @Modified:
 */

/**
 * 可存档实体
 */
public interface IArchivedEntity extends INamedEntity{
    boolean isDeleted();
    String getTag();
}
