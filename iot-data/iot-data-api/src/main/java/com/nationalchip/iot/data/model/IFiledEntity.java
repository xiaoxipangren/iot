package com.nationalchip.iot.data.model;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 9:40 AM
 * @Modified:
 */

/**
 * 需要与文件系统相关联的实体
 */
public interface IFiledEntity extends IVersionedEntity {
    String getFileName();
    String getSha1();
    long getSize();
}
