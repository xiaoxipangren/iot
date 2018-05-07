package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.INamedEntity;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/2/18 4:47 PM
 * @Modified:
 */
public interface INamedBuilder<T extends INamedEntity> extends IBuilder<T> {
    INamedBuilder<T> name(String name);
    Optional<String> getName();
    INamedBuilder<T> description(String description);
}
