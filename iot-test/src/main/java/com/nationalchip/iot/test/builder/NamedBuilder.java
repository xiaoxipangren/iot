package com.nationalchip.iot.test.builder;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/3/18 1:11 PM
 * @Modified:
 */
public class NamedBuilder<T extends ICreupdate> extends BaseBuilder<T> implements INamedBuilder<T> {

    protected String name;

    @Override
    public T name(String name) {
        this.name=name;
        return (T)this;
    }


    public Optional<String> getName(){
        return Optional.ofNullable(name);
    }

}
