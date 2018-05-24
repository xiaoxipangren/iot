package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.INamedEntity;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/3/18 2:34 PM
 * @Modified:
 */
public abstract class NamedCreupdate<T extends INamedBuilder<? extends INamedEntity>> extends BaseCreupdate<T> implements INamedCreupdate<T> {
    private String name;
    private String description;

    @Override
    public T name(String name) {
        this.name=name;
        return self();
    }

    public Optional<String> getName(){
        return Optional.ofNullable(name);
    }


    public Optional<String> getDescription(){
        return Optional.ofNullable(description);
    }


    @Override
    public T description(String description) {
        this.description=description;
        return self();
    }
}
