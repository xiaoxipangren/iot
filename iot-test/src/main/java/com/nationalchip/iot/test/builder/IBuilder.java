package com.nationalchip.iot.test.builder;


/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/3/18 10:51 AM
 * @Modified:
 */
public interface IBuilder<T extends ICreupdate>{
    long getId();
    T id(long id);
}
