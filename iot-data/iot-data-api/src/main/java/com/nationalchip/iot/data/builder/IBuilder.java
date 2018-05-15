package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.IEntity;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/2/18 9:22 AM
 * @Modified:
 */
public interface IBuilder<E extends IEntity>{
    IBuilder<E> id(long id);
    Optional<Long> getId();
    E create();
    void update(final E e);
}
