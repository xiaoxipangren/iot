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
    public IAssetBuilder asset() {
        return new AssetBuilder();
    }

    @Override
    public INewsBuilder news() {
        return new NewsBuilder();
    }

    @Override
    public IDocumentBuilder document() {
        return new DocumentBuilder();
    }


    @Override
    public IRoleBuilder role(){
        return new RoleBuilder();
    }



}
