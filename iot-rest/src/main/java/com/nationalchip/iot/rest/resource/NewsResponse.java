package com.nationalchip.iot.rest.resource;

import java.util.Date;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/28/18 2:17 PM
 * @Modified:
 */
public class NewsResponse extends ArchivedResponse {

    private Date date;
    private String author;
    private String content;
    private String cover;
    private String abstraction;
    private boolean sticky;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAbstraction() {
        return abstraction;
    }

    public void setAbstraction(String abstraction) {
        this.abstraction = abstraction;
    }

    public boolean isSticky() {
        return sticky;
    }

    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }
}
