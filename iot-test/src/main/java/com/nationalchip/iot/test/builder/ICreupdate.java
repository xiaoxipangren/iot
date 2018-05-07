package com.nationalchip.iot.test.builder;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/3/18 1:07 PM
 * @Modified:
 */
public interface ICreupdate<E extends IEntity> {
    E create();
    E update(E e);
}
