package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.INamedEntity;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/3/18 1:55 PM
 * @Modified:
 */


public interface INamedCreupdate<T extends INamedBuilder<E>,E extends INamedEntity> extends ICreupdate<T,E>{
    T name(String name);
    T description(String description);
}
