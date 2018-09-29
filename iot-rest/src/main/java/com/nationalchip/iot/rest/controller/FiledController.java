package com.nationalchip.iot.rest.controller;

import com.nationalchip.iot.data.builder.IFiledBuilder;
import com.nationalchip.iot.data.manager.IFiledManager;
import com.nationalchip.iot.data.model.IFiledEntity;
import com.nationalchip.iot.rest.exception.RestException;
import com.nationalchip.iot.rest.resource.*;
import static com.nationalchip.iot.security.configuration.RestMapping.*;
import com.nationalchip.iot.tenancy.ITenantAware;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private ITenantAware tenantAware;

    @RequestMapping(value = REST_DOWNLOAD_ACTION,method= RequestMethod.GET)
    public ResponseEntity<ByteArrayResource> download(@PathVariable(value = "id")long id){

        T entity = getManager().findOne(id);

        if(!entity.isShared() && tenantAware.isAnonymous())
            throw new RestException("权限不足，请登录后下载",HttpStatus.FORBIDDEN);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(entity.getSize());
        if(entity.getFileName()!=null && !entity.getFileName().isEmpty()){
            headers.setContentDispositionFormData("attachment", entity.getFileName());
        }

        return new ResponseEntity<>(new ByteArrayResource(getAssembler().toBytes(entity)),headers, HttpStatus.OK);
    }

    @RequestMapping(value = REST_UPLOAD_ACTION,method= RequestMethod.POST)
    public ResponseEntity<Response> upload(@PathVariable(value = "id")long id, Q request){
        return super.update(id,request);
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
