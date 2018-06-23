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
public class AssetController extends BaseController<IAsset,AssetResponse,IAssetBuilder,AssetRequest> {


    @RequestMapping(method= RequestMethod.POST)
    public ResponseEntity<Response> create(@RequestParam(value = "file") MultipartFile file,
                                           final AssetRequest request){

        try {

            if(!request.getFileName().isPresent()){
                request.setFileName(file.getOriginalFilename());
            }

            IAssetBuilder builder = getAssembler().fromRequest(request);

            builder.stream(file.getInputStream()).size(file.getSize());


            IAsset asset = getManager().create(builder);

            return Response.created("资源创建成功",getAssembler().toResource(asset));
        } catch (IOException e) {
            throw new AssetException("资源创建失败："+e.getMessage());
        }

    }

    @RequestMapping(value = RestConstant.REST_DOWNLOAD_ACTION,method= RequestMethod.GET)
    public ResponseEntity<ByteArrayResource> download(@PathVariable(value = "id")long id){

        IAsset asset = getManager().findOne(id);

        File file = getManager().getFile(asset.getSha1());

        byte[] data=null;
        try {
            data = FileCopyUtils.copyToByteArray(file);

        } catch (IOException e) {
            throw new AssetException(e.getMessage());
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(file.length());
        if(asset.getFileName()!=null && !asset.getFileName().isEmpty()){
            headers.setContentDispositionFormData("attachment", asset.getFileName());
        }

        return new ResponseEntity<>(new ByteArrayResource(data),headers, HttpStatus.OK);
    }



    @RequestMapping(value = RestConstant.REST_ID_MAPPING,method= RequestMethod.PATCH)
    public ResponseEntity<Response> update(@PathVariable("id")final Long id, @RequestBody final AssetRequest request){

        IAssetBuilder builder = getAssembler().fromRequest(request);
        builder.id(id);
        IAsset asset = getManager().update(builder);

        return Response.ok(getAssembler().toResource(asset));
    }

    @Override
    public AssetAssembler getAssembler() {
        return (AssetAssembler)super.getAssembler();
    }

    @Override
    public AssetManager getManager() {
        return (AssetManager) super.getManager();
    }
}
