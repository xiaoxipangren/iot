package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/28/18 3:39 PM
 * @Modified:
 */
public interface IManager<T extends BaseEntity,I extends Serializable> {
    T get(I id);
    T create(T t);
    Iterable<T> getAll();
    void delete(I id);
    T update(T t);
}
