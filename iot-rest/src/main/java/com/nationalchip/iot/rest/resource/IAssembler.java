package com.nationalchip.iot.rest.resource;

import com.nationalchip.iot.data.builder.IBuilder;
import com.nationalchip.iot.data.model.IEntity;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/22/18 2:58 PM
 * @Modified:
 */
public interface IAssembler<T extends IEntity,R extends IResponse,B extends IBuilder<? extends T>,Q extends IRequest> {
    R toResource(T entity);
    B fromRequest(Q request);
}
