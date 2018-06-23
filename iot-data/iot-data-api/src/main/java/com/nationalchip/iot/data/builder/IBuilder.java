package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.IEntity;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/2/18 9:22 AM
 * @Modified:
 */
public interface IBuilder<T extends IEntity>{
    IBuilder<T> id(long id);
    Optional<Long> getId();
    T create();
    void update(final T t);
}
