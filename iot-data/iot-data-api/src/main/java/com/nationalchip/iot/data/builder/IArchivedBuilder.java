package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.IArchivedEntity;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 4:49 PM
 * @Modified:
 */
public interface IArchivedBuilder<T extends IArchivedEntity> extends INamedBuilder<T> {
    IArchivedBuilder<T> deleted(boolean deleted);
    Optional<Boolean> isDeleted();

    IArchivedBuilder<T> tag(String tag);
    Optional<String> getTag();
}
