package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.IEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@NoRepositoryBean
@Transactional(readOnly=true)
public interface IRepository<T extends IEntity> extends PagingAndSortingRepository<T,Long> {
    Optional<T> findById(Long id);
}
