package com.nationalchip.iot.data.model;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/10/18 10:49 AM
 * @Modified:
 */
public interface ICategory extends INamedEntity {
    String getDetail();//类别细节描述
    String getCode();//类别编码
}
