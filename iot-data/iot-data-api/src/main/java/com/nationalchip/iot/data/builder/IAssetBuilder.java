package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.IAsset;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 4:55 PM
 * @Modified:
 */
public interface IAssetBuilder extends IFiledBuilder<IAsset> {
    IAssetBuilder guide(String guide);
}