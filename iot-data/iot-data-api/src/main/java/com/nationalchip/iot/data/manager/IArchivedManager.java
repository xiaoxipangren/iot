package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.IArchivedEntity;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 3:46 PM
 * @Modified:
 */
public interface IArchivedManager<T extends IArchivedEntity> extends INamedManager<T> {
    void softDelete(Long id);
    void softDelete(Iterable<T> entities);
    void softDeleteAll();
}
