package com.nationalchip.iot.rest.resource;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/22/18 3:49 PM
 * @Modified:
 */
public abstract class FiledRequest extends VersionedRequest {
    private String fileName;
    private MultipartFile file;
    private String sha1;
    private boolean shared;

    public Optional<Boolean> isShared() {
        return Optional.ofNullable(shared);
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }
    public Optional<String> getSha1() {
        return Optional.ofNullable(sha1);
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public Optional<String> getFileName() {
        return Optional.ofNullable(fileName);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Optional<MultipartFile> getFile() {
        return Optional.ofNullable(file);
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
