package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.IArchivedEntity;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 4:57 PM
 * @Modified:
 */
public interface IArchivedCreupdate<T extends IArchivedBuilder<? extends IArchivedEntity>> extends INamedCreupdate<T>  {
    T deleted(boolean deleted);
}
