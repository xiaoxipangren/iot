package com.nationalchip.iot.data.model;

import com.nationalchip.iot.data.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/4/18 10:10 AM
 * @Modified:
 */
@Table(name = "document")
@Entity
public class Document extends NamedEntity implements IDocument {


    @Column(name="url")
    @Comment("文档在线地址")
    private String url;

    @Override
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
