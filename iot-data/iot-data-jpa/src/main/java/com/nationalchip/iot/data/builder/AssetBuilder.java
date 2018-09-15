package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.Asset;
import com.nationalchip.iot.data.model.IAsset;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 5:19 PM
 * @Modified:
 */
public class AssetBuilder extends FiledCreupdate<IAssetBuilder,IAsset> implements IAssetBuilder {

    private String guide;




    @Override
    public IAssetBuilder guide(String guide) {
        this.guide=guide;
        return self();
    }


    @Override
    protected void apply(IAsset entity) {
        super.apply(entity);
        this.<Asset>tryCast(entity).ifPresent(
                r -> {
                    if(guide!=null){
                        r.setGuide(guide);
                    }
                }
        );
    }

    @Override
    protected IAsset newInstance() {
        return new Asset();
    }
}
