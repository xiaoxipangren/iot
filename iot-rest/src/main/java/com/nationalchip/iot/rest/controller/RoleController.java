package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.model.auth.IRole;
import com.nationalchip.iot.rest.resource.RoleResponse;
import com.nationalchip.iot.data.builder.IRoleBuilder;
import com.nationalchip.iot.rest.resource.RoleRequest;
import com.nationalchip.iot.rest.resource.Response;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import static com.nationalchip.iot.security.configuration.RestMapping.*;
import static com.nationalchip.iot.security.authority.AuthorityExpression.*;

@RestController
@RequestMapping(value=REST_BASE_MAPPING+REST_ROLE_MAPPING)
public class RoleController extends BaseController<IRole,RoleResponse,IRoleBuilder,RoleRequest>{

    @Override
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize(HAS_AUTH_CREATE_ROLE)
    public ResponseEntity<Response> create(RoleRequest request){
        return super.create(request);
    }

    @Override
    @RequestMapping(method = RequestMethod.PATCH,value = REST_ID_MAPPING)
    @PreAuthorize(HAS_AUTH_UPDATE_ROLE)
    public ResponseEntity<Response> update(@PathVariable final Long id,@RequestBody RoleRequest request){
        return super.update(id,request);
    }

    @Override
    @RequestMapping(method = RequestMethod.DELETE,value = REST_ID_MAPPING)
    @PreAuthorize(HAS_AUTH_DELETE_ROLE)
    public ResponseEntity<Response> delete(@PathVariable final Long id){
        return super.delete(id);
    }

}

