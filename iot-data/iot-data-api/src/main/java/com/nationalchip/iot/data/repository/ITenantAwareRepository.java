package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.ITenantAwareEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;


@NoRepositoryBean
@Transactional(readOnly=true)
public interface ITenantAwareRepository<T extends ITenantAwareEntity> extends IRepository<T> {
    Iterable<T> findByTenant(String tenant);
}
