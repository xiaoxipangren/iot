package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.IFiledEntity;

import java.io.InputStream;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 5:00 PM
 * @Modified:
 */
public interface IFiledCreupdate<T extends IFiledBuilder<? extends IFiledEntity>>  extends IVersionedCreupdate<T>{
    T sha1(String sha1);
    T fileName(String fileName);
    T size(Long size);
    T content(InputStream content);
}
