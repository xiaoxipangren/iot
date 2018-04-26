package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/28/18 3:39 PM
 * @Modified:
 */
public interface IManager<T extends BaseEntity> {
    T get(Long id);
    Iterable<T> getAll();
    Iterable<T> get(Iterable<Long> ids);
    Iterable<T> getAll(Sort sort);
    Page<T> getAll(Pageable pageable);



    boolean exists(Long id);

    T update(T t);
    T create(T t);

    long count();

    void delete(Long id);
    void delete(Iterable<T> entities);
    void deleteAll();


}