package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.INews;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/15/18 9:15 AM
 * @Modified:
 */
public interface INewsManager extends IArchivedManager<INews> {
    Iterable<INews> findTricky();
}
