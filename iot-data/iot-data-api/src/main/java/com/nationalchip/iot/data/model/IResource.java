package com.nationalchip.iot.data.model;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/22/18 9:35 AM
 * @Modified:
 */

import java.io.InputStream;

/**
 * 下载资源接口
 */

public interface IResource extends IFiledEntity {
    String getGuide();
    String getCatagory();
}