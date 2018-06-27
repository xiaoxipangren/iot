package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.builder.IAssetBuilder;
import com.nationalchip.iot.data.manager.AssetManager;
import com.nationalchip.iot.data.model.IAsset;
import com.nationalchip.iot.rest.exception.AssetException;
import com.nationalchip.iot.rest.resource.*;
import com.nationalchip.iot.security.configuration.RestConstant;
import io.swagger.annotations.Api;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 4:45 PM
 * @Modified:
 */
@RestController
@RequestMapping(value = RestConstant.REST_BASE_MAPPING+RestConstant.REST_ASSET_MAPPING)
@Api(tags = "下载资源API")
public class AssetController extends FiledController<IAsset,AssetResponse,IAssetBuilder,AssetRequest> {

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Response> create(AssetRequest request) {
        return super.create(request);
    }
}
