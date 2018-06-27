package com.nationalchip.iot.data.builder;

import com.nationalchip.iot.data.model.FiledEntity;
import com.nationalchip.iot.data.model.IFiledEntity;

import javax.swing.text.html.parser.Entity;
import java.io.InputStream;
import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 5:11 PM
 * @Modified:
 */
public abstract class FiledCreupdate<T extends IFiledBuilder<E>,E extends IFiledEntity>  extends VersionedCreupdate<T,E> implements IFiledCreupdate<T,E> {
    private String sha1;
    private String fileName;
    private Long size;
    private InputStream stream;

    public T stream(InputStream stream) {
        this.stream =stream;
        return self();
    }

    @Override
    public T sha1(String sha1) {
        this.sha1=sha1;
        return self();
    }

    @Override
    public T fileName(String fileName) {
        this.fileName=fileName;
        return self();
    }

    @Override
    public T size(Long size) {
        this.size=size;
        return self();
    }


    public Optional<String> getFileName(){
        return Optional.ofNullable(fileName);
    }

    public Optional<String> getSha1(){
        return Optional.ofNullable(sha1);
    }
    public Optional<Long> getSize(){
        return Optional.ofNullable(size);
    }

    public Optional<InputStream> getStream() {
        return Optional.ofNullable(stream);
    }


    @Override
    protected void apply(E entity) {
        super.apply(entity);
        this.<FiledEntity>tryCast(entity).ifPresent(
                e ->{
                    getFileName().ifPresent(fileName -> e.setFileName(fileName));
                    getSha1().ifPresent(sha1 -> e.setSha1(sha1));
                    getStream().ifPresent(stream -> {
                        e.setStream(stream);
                    });
                    getSize().ifPresent(size -> e.setSize(size));
                }
        );

    }
}
