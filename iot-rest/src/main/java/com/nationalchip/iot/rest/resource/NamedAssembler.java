package com.nationalchip.iot.rest.resource;

import com.nationalchip.iot.data.builder.INamedBuilder;
import com.nationalchip.iot.data.model.INamedEntity;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/21/18 5:04 PM
 * @Modified:
 */
public abstract class NamedAssembler<T extends INamedEntity, R extends NamedResponse,B extends INamedBuilder<T>,Q extends NamedRequest> extends BaseAssembler<T, R,B,Q> {
    public NamedAssembler(Class<?> controllerClass, Class<R> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public R toResource(T entity) {
        R r = super.toResource(entity);
        r.setDescription(entity.getDescription());
        r.setName(entity.getName());

        return r;
    }

    @Override
    public B fromRequest(Q request) {
        B b = super.fromRequest(request);
        request.getName().ifPresent(name -> b.name(name));
        request.getDescription().ifPresent(des -> b.description(des));

        return b;
    }
}
