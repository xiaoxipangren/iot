package com.nationalchip.iot.data.model;

import com.nationalchip.iot.data.annotation.Comment;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.InputStream;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 10:02 AM
 * @Modified:
 */
@MappedSuperclass
public class FiledEntity extends VisionedEntity implements IFiledEntity {

    @Column(name="filename")
    @Comment("上传时的文件名")
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    @Column(name="sha1",unique = true)
    @Comment("sha1值")
    private String sha1;

    @Column(name="size")
    @Comment("文件大小")
    private long size;

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getSha1() {
        return sha1;
    }

    @Override
    public long getSize() {
        return size;
    }

    public void setConent(InputStream conent) {
        this.conent = conent;
    }

    @Override
    public InputStream getContent() {
        return conent;
    }

    @Transient
    private InputStream conent;
}
