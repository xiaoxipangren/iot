package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.IVersionedEntity;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 4:52 PM
 * @Modified:
 */
public interface IVersionedBuilder<T extends IVersionedEntity> extends IArchivedBuilder<T> {
    IVersionedBuilder<T> version(String version);
    Optional<String> getVersion();

}
