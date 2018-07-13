package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.builder.IFiledBuilder;
import com.nationalchip.iot.data.manager.IFiledManager;
import com.nationalchip.iot.data.model.IFiledEntity;
import com.nationalchip.iot.rest.resource.FiledAssembler;
import com.nationalchip.iot.rest.resource.FiledRequest;
import com.nationalchip.iot.rest.resource.FiledResponse;
import com.nationalchip.iot.rest.resource.Response;
import com.nationalchip.iot.security.configuration.RestMappingConstant;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/24/18 1:22 PM
 * @Modified:
 */
public abstract class FiledController<T extends IFiledEntity,R extends FiledResponse,B extends IFiledBuilder<T>,Q extends FiledRequest>  extends BaseController<T,R,B,Q>{

    @RequestMapping(value = RestMappingConstant.REST_DOWNLOAD_ACTION,method= RequestMethod.GET)
    public ResponseEntity<ByteArrayResource> download(@PathVariable(value = "id")long id){

        T entity = getManager().findOne(id);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(entity.getSize());
        if(entity.getFileName()!=null && !entity.getFileName().isEmpty()){
            headers.setContentDispositionFormData("attachment", entity.getFileName());
        }

        return new ResponseEntity<>(new ByteArrayResource(getAssembler().toBytes(entity)),headers, HttpStatus.OK);
    }

    @Override
    public IFiledManager<T> getManager() {
        return (IFiledManager<T>)super.getManager();
    }

    @Override
    public FiledAssembler<T, R, B, Q> getAssembler() {
        return (FiledAssembler<T,R,B,Q>)super.getAssembler();
    }
}
