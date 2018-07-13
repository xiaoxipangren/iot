package com.nationalchip.iot.rest.resource;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/3/18 10:12 AM
 * @Modified:
 */
public class MailRequest{
    private String to;
    private String title;
    private String subject;
    private String content;
    private String action;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
