package com.nationalchip.iot.rest.resource;

import com.nationalchip.iot.data.builder.AssetBuilder;
import com.nationalchip.iot.data.builder.IAssetBuilder;
import com.nationalchip.iot.data.model.IAsset;
import com.nationalchip.iot.rest.controller.AssetController;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/22/18 11:02 AM
 * @Modified:
 */

@Component
public class AssetAssembler extends FiledAssembler<IAsset,AssetResponse,IAssetBuilder,AssetRequest> {

    public AssetAssembler(){
        super(AssetController.class,AssetResponse.class);
    }

    @Override
    public AssetResponse toResource(IAsset entity) {
        AssetResponse resource =  super.toResource(entity);
        resource.setGuide(entity.getGuide());
        resource.setShared(entity.isShared());

//        resource.add(linkTo(methodOn(AssetController.class).download(entity.getId())).withRel("download"));

        return resource;

    }

    @Override
    protected IAssetBuilder builder() {
        return getBuilderFactory().asset();
    }

    @Override
    public IAssetBuilder fromRequest(AssetRequest request) {
        IAssetBuilder builder = super.fromRequest(request);
        request.getGuide().ifPresent(guide -> builder.guide(guide));
        request.isShared().ifPresent(shared -> builder.shared(shared));
        return builder;
    }
}
