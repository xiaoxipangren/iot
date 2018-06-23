package com.nationalchip.iot.rest.resource;

import com.nationalchip.iot.data.builder.IArchivedBuilder;
import com.nationalchip.iot.data.model.IArchivedEntity;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/21/18 7:17 PM
 * @Modified:
 */
public abstract class ArchivedAssembler<T extends IArchivedEntity, R extends ArchivedResponse,B extends IArchivedBuilder<T>,Q extends ArchivedRequest> extends NamedAssembler<T, R,B,Q> {
    @Override
    public R toResource(T entity) {
        R r = super.toResource(entity);
        r.setTag(entity.getTag());
        r.setDeleted(entity.isDeleted());
        return r;
    }

    public ArchivedAssembler(Class<?> controllerClass, Class<R> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public B fromRequest(Q request) {
        B b = super.fromRequest(request);
        request.isDeleted().ifPresent( d -> b.deleted(d));
        request.getTag().ifPresent(tag -> b.tag(tag));

        return b;
    }
}
