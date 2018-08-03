package com.nationalchip.iot.data.model;

import com.nationalchip.iot.data.annotation.Comment;

import javax.persistence.*;
import java.io.InputStream;
import java.util.Date;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/14/18 4:23 PM
 * @Modified:
 */
@Entity
@Table(name = "news")
public class News extends ArchivedEntity implements INews {

    @Comment("新闻日期")
    @Column(name="date")
    private Date date;

    @Comment("新闻作者")
    @Column(name="author")
    private String author;

    @Lob
    @Comment("新闻内容")
    @Column(name="content")
    private String content;

    @Comment("新闻封面图片")
    @Column(name="cover")
    private String cover;

    @Comment("标记是否置顶")
    @Column(name="sticky")
    private boolean sticky;


    @Override
    public InputStream getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(InputStream coverImage) {
        this.coverImage = coverImage;
    }

    @Transient
    private InputStream coverImage;


    public String getAbstraction() {
        return abstraction;
    }

    public void setAbstraction(String abstraction) {
        this.abstraction = abstraction;
    }

    @Comment("新闻摘要")
    @Column(name="abstract")
    private String abstraction;

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }

    public void setDate(Date date) {

        this.date = date;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getCover() {
        return cover;
    }

    @Override
    public boolean isSticky() {
        return sticky;
    }
}
