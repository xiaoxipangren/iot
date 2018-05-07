package com.nationalchip.iot.test.builder;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/3/18 1:09 PM
 * @Modified:
 */
public interface INamedBuilder<T extends ICreupdate> extends IBuilder<T> {
    T name(String name);

}
