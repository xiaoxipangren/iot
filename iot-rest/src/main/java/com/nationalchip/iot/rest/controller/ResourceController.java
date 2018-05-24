package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.builder.IBuilderFactory;
import com.nationalchip.iot.data.builder.IResourceBuilder;
import com.nationalchip.iot.data.manager.IResourceManager;
import com.nationalchip.iot.data.model.IResource;
import com.nationalchip.iot.rest.exception.ResourceException;
import com.nationalchip.iot.rest.model.RestResult;
import com.nationalchip.iot.rest.model.resource.ResourceDto;
import com.nationalchip.iot.security.configuration.RestConstant;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 4:45 PM
 * @Modified:
 */
@RestController
@RequestMapping(value = RestConstant.REST_BASE_MAPPING+"/resource")
@Api(tags = "下载资源API")
public class ResourceController extends BaseController {

    @Autowired
    private IResourceManager resourceManager;

    @Autowired
    private IBuilderFactory builderFactory;


    @RequestMapping(value = "/upload",method= RequestMethod.POST)
    public ResponseEntity<RestResult> upload(@RequestParam(value = "file") MultipartFile file,
                                             @RequestParam(value = "category") String category,
                                             @RequestParam(value = "name") String name,
                                             @RequestParam(value = "sha1",required = false)String sha1,
                                             @RequestParam(value="fileName",required = false)String fileName){

        try {
            IResourceBuilder builder = builderFactory.resource();
            builder.category(category);
            builder.content(file.getInputStream());
            builder.sha1(sha1);
            builder.fileName(fileName);
            builder.size(file.getSize());
            builder.name(name);

            resourceManager.create(builder);

            return ok("上传资源成功");
        } catch (IOException e) {
            throw new ResourceException("上传资源失败："+e.getMessage());
        }

    }

    @RequestMapping(value = "/download",method= RequestMethod.GET)
    public ResponseEntity<ByteArrayResource> download(@RequestParam(value = "id")String sha1){

        File file = resourceManager.getBySha1(sha1);

        byte[] data=null;
        try {
            data = FileCopyUtils.copyToByteArray(file);

        } catch (IOException e) {
            throw new ResourceException(e.getMessage());
        }


        IResource resource = resourceManager.findBySha1(sha1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(file.length());
        headers.setContentDispositionFormData("attachment", resource.getFileName());

        return new ResponseEntity<>(new ByteArrayResource(data),headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/list",method= RequestMethod.GET)
    public ResponseEntity<RestResult> list(@RequestParam(value = "category")String category){
        List<ResourceDto> list = new ArrayList<>();
        resourceManager.findByCategory(category).forEach(r -> list.add(map(r)));
        return ok(list);
    }

    private ResourceDto map(IResource resource){
        ResourceDto dto = new ResourceDto();
        dto.setCategory(resource.getCatagory());
        dto.setDescription(resource.getDescription());
        dto.setName(resource.getName());
        dto.setSha1(resource.getSha1());

        return dto;
    }


}
