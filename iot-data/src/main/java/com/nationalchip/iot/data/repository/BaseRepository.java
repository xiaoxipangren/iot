package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.BaseEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/28/18 3:36 PM
 * @Modified:
 */
public interface BaseRepository<T extends BaseEntity>  extends IRepository<T> {

}
