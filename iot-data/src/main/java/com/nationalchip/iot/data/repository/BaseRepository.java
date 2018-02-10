package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
@Transactional(readOnly=true)
public interface BaseRepository<T extends BaseEntity,I extends Serializable> extends PagingAndSortingRepository<T,I> {


    Optional<T> findById(I id);

    T getById(I id);

}
