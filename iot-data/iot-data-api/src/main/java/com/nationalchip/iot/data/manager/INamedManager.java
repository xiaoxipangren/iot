package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.builder.INamedBuilder;
import com.nationalchip.iot.data.model.INamedEntity;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/2/18 1:29 PM
 * @Modified:
 */
public interface INamedManager<T extends INamedEntity> extends IManager<T> {
    T update(INamedBuilder<T> builder);
    boolean existsByName(String name);
}
