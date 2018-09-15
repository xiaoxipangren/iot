package com.nationalchip.iot.data.model;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 9:40 AM
 * @Modified:
 */

import java.io.InputStream;

/**
 * 需要与文件系统相关联的实体
 */
public interface IFiledEntity extends IVersionedEntity {
    String getFileName();
    String getSha1();
    long getSize();
    InputStream getStream();
    /*资源是否是共享的，是可以自由下载，否登录才能下载*/
    boolean isShared();
}
