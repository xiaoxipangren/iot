package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.ITenantAwareEntity;
import com.nationalchip.iot.data.model.TenantAwareEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;


@NoRepositoryBean
@Transactional(readOnly=true)
public interface TenantAwareRepository<T extends TenantAwareEntity> extends IRepository<T> {

}
