package com.nationalchip.iot.rest.resource;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/28/18 2:17 PM
 * @Modified:
 */
public class NewsRequest extends ArchivedRequest {

    private Date date;

    private String author;

    private String content;

    private MultipartFile cover;

    private boolean sticky;

    private String abstraction;


    public Optional<Date> getDate() {
        return  Optional.ofNullable(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Optional<String> getAuthor() {
        return Optional.ofNullable(author);
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Optional<String> getContent() {
        return Optional.ofNullable(content);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Optional<MultipartFile> getCover() {
        return Optional.ofNullable(cover);
    }

    public void setCover(MultipartFile cover) {
        this.cover = cover;
    }

    public Optional<Boolean> isSticky() {
        return Optional.ofNullable(sticky);
    }

    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }

    public Optional<String> getAbstraction() {
        return Optional.ofNullable(abstraction);
    }

    public void setAbstraction(String abstraction) {
        this.abstraction = abstraction;
    }
}
