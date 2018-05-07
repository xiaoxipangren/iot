package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.INamedEntity;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/2/18 10:45 AM
 * @Modified:
 */
@NoRepositoryBean
public interface INamedRepository<T extends INamedEntity> extends IRepository<T> {
    T findByName(String name);
}
