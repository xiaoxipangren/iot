package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.model.BaseEntity;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/28/18 4:00 PM
 * @Modified:
 */


public interface IController<T extends BaseEntity> {

    Iterable<T> list();

    T find(Long id);

    T create()


}
