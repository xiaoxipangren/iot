package com.nationalchip.iot.data.repository;

import com.nationalchip.iot.data.model.News;
import org.springframework.data.domain.Page;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/14/18 8:36 PM
 * @Modified:
 */
public interface NewsRepository extends IArchiveRepository<News> {

    Iterable<News> findByStickyOrderByDateDesc(boolean sticky);
}
