package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.IFiledEntity;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 4:53 PM
 * @Modified:
 */
public interface IFiledBuilder<T extends IFiledEntity> extends IVersionedBuilder<T> {
    IFiledBuilder<T> sha1(String sha1);
    IFiledBuilder<T> fileName(String fileName);
    IFiledBuilder<T> size(Long size);
}
