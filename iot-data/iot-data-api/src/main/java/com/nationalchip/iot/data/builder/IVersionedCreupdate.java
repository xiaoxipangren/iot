package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.IVersionedEntity;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 4:59 PM
 * @Modified:
 */
public interface IVersionedCreupdate<T extends IVersionedBuilder<? extends IVersionedEntity>> extends IArchivedCreupdate<T> {
    T version(String version);
}
