package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.builder.IAssetBuilder;
import com.nationalchip.iot.data.model.IAsset;
import com.nationalchip.iot.rest.resource.*;
import com.nationalchip.iot.security.configuration.RestMappingConstant;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 4:45 PM
 * @Modified:
 */
@RestController
@RequestMapping(value = RestMappingConstant.REST_BASE_MAPPING+ RestMappingConstant.REST_ASSET_MAPPING)
@Api(tags = "下载资源API")
public class AssetController extends FiledController<IAsset,AssetResponse,IAssetBuilder,AssetRequest> {

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Response> create(AssetRequest request) {
        return super.create(request);
    }



    @RequestMapping(value = RestMappingConstant.REST_ID_MAPPING,method = RequestMethod.PATCH)
    @Override
    public ResponseEntity<Response> update(@PathVariable final Long id,@RequestBody AssetRequest request) {
        return super.update(id, request);
    }
}
