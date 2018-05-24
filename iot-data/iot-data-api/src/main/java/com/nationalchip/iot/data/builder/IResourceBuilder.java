package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.IResource;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 4:55 PM
 * @Modified:
 */
public interface IResourceBuilder extends IFiledBuilder<IResource> {
    IResourceBuilder guide(String guide);
}
