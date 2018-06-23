package com.nationalchip.iot.data.manager;

import com.nationalchip.iot.data.model.IAsset;
import com.nationalchip.iot.data.model.Asset;
import org.springframework.stereotype.Component;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 4:33 PM
 * @Modified:
 */

@Component
public class AssetManager extends FiledManager<IAsset,Asset> implements IAssetManager {


}
