package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.IFiledEntity;

import java.io.File;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 7:16 PM
 * @Modified:
 */
public interface IFiledManager<T extends IFiledEntity> extends IArchivedManager<T> {

    boolean existsBySha1(String sha1);
    void deleteBySha1(String sha1);
    T findBySha1(String sha1);

    File getBySha1(String sha1);

}
