package com.nationalchip.iot.rest.resource;

import com.google.common.io.ByteStreams;
import com.nationalchip.iot.data.builder.IFiledBuilder;
import com.nationalchip.iot.data.model.IFiledEntity;
import com.nationalchip.iot.rest.exception.FileReadException;

import java.io.IOException;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/21/18 7:21 PM
 * @Modified:
 */
public abstract class FiledAssembler<T extends IFiledEntity, R extends FiledResponse,B extends IFiledBuilder<T>,Q extends FiledRequest> extends VersionedAssembler<T, R,B,Q> {
    public FiledAssembler(Class<?> controllerClass, Class<R> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    public R toResource(T entity) {
        R r =  super.toResource(entity);
        r.setFileName(entity.getFileName());
        r.setSha1(entity.getSha1());
        r.setSize(entity.getSize());

        return r;
    }

    public byte[] toBytes(T entity){

        try {
            return ByteStreams.toByteArray(entity.getStream());
        } catch (IOException e) {
            throw new FileReadException(e.getMessage());
        }
    }



    @Override
    public B fromRequest(Q request) {
        B b = super.fromRequest(request);
        request.getFileName().ifPresent(name -> b.fileName(name));
        request.getSha1().ifPresent(sha1 -> b.sha1(sha1));
        request.getFile().ifPresent(file -> {
            try {
                b.stream(file.getInputStream());
                b.size(file.getSize());
                if(!request.getFileName().isPresent()){
                    b.fileName(file.getOriginalFilename());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return b;
    }
}
