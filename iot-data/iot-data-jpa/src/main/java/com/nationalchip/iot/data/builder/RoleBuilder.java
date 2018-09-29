package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.auth.Role;
import com.nationalchip.iot.data.model.auth.IRole;

public class RoleBuilder extends NamedCreupdate<IRoleBuilder,IRole> implements IRoleBuilder{

    @Override
    protected IRole newInstance(){
        return new Role();
    }

    @Override
    protected void apply(IRole entity){
        super.apply(entity);
        this.<Role>tryCast(entity).ifPresent(
                r -> {

                }

        );
    }

}

