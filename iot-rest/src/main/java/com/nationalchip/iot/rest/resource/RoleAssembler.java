package com.nationalchip.iot.rest.resource;

import com.nationalchip.iot.data.model.auth.IRole;
import com.nationalchip.iot.data.builder.IRoleBuilder;
import com.nationalchip.iot.rest.controller.RoleController;
import org.springframework.stereotype.*;

@Component
public class RoleAssembler extends NamedAssembler<IRole,RoleResponse,IRoleBuilder,RoleRequest>{

    public  RoleAssembler(){
        super(RoleController.class,RoleResponse.class);
    }

    @Override
    protected IRoleBuilder builder(){
        return getBuilderFactory().role();
    }

    @Override
    public RoleResponse toResource(IRole entity){
        RoleResponse response = super.toResource(entity);
        return response;
    }

    @Override
    public IRoleBuilder fromRequest(RoleRequest request){
        IRoleBuilder builder = super.fromRequest(request);
        return builder;
    }

}

