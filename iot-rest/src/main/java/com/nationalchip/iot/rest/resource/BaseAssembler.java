package com.nationalchip.iot.rest.resource;

import com.nationalchip.iot.data.builder.IBuilder;
import com.nationalchip.iot.data.builder.IBuilderFactory;
import com.nationalchip.iot.data.model.IEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/21/18 4:32 PM
 * @Modified:
 */
public abstract class BaseAssembler<T extends IEntity, R extends BaseResponse,B extends IBuilder<T>,Q extends BaseRequest> extends IdentifiableResourceAssemblerSupport<T, R> implements IAssembler<T,R,B,Q> {

    public IBuilderFactory getBuilderFactory() {
        return builderFactory;
    }

    public void setBuilderFactory(IBuilderFactory builderFactory) {
        this.builderFactory = builderFactory;
    }

    @Autowired
    private IBuilderFactory builderFactory;

    public BaseAssembler(Class<?> controllerClass, Class<R> resourceType) {
        super(controllerClass, resourceType);
    }


    @Override
    public R toResource(T entity) {
        R r = createResource(entity);

        r.setCreatedAt(entity.getCreatedAt());
        r.setCreatedBy(entity.getCreatedBy());
        r.setLastModifiedAt(entity.getLastModifiedAt());
        r.setLastModifiedBy(entity.getLastModifiedBy());
        r.setIdentity(entity.getId());

        return r;
    }


    @Override
    public B fromRequest(Q request) {

        return builder();

    }

    protected abstract B builder();


}
