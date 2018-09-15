package com.nationalchip.iot.data.model;

import com.nationalchip.iot.data.model.auth.IUser;

import java.util.Set;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 8/3/18 2:37 PM
 * @Modified:
 */
public interface IDocument extends INamedEntity{
    String getUrl();//文档浏览地址
}

