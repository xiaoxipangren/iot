package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.ITenantAwareEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;


@NoRepositoryBean
@Transactional(readOnly=true)
public interface TenantAwareRepository<T extends ITenantAwareEntity> extends BaseRepository<T> {
    T findByTenant(String tenant);
}
