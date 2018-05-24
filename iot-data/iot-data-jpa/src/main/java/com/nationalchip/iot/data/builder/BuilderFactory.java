package com.nationalchip.iot.data.builder;

import org.springframework.stereotype.Component;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/7/18 1:50 PM
 * @Modified:
 */

@Component
public class BuilderFactory implements IBuilderFactory {
    @Override
    public IUserBuilder user() {
        return new UserBuilder();
    }

    @Override
    public IResourceBuilder resource() {
        return new ResourceBuilder();
    }
}
