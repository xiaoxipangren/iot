package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.IEntity;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/3/18 1:52 PM
 * @Modified:
 */
public interface ICreupdate<T extends IBuilder<? extends IEntity>> {
    T id(long id);
}
