package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
@Transactional(readOnly=true)
public interface IRepository<T extends BaseEntity> extends PagingAndSortingRepository<T,Long> {
    Optional<T> findById(Long id);
}
