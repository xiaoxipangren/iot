package com.nationalchip.iot.rest.resource;

import com.nationalchip.iot.data.builder.IVersionedBuilder;
import com.nationalchip.iot.data.model.IVersionedEntity;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/21/18 7:19 PM
 * @Modified:
 */
public abstract class VersionedAssembler<T extends IVersionedEntity, R extends VersionedResponse,B extends IVersionedBuilder<T>,Q extends VersionedRequest> extends ArchivedAssembler<T, R,B,Q> {
    public VersionedAssembler(Class<?> controllerClass, Class<R> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public R toResource(T entity) {
        R r = super.toResource(entity);

        r.setVersion(entity.getVersion());

        return r;
    }

    @Override
    public B fromRequest(Q request) {
        B b = super.fromRequest(request);
        request.getVersion().ifPresent(v -> b.version(v));

        return b;
    }
}
