package com.nationalchip.iot.rest.resource;

import org.springframework.hateoas.ResourceSupport;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/3/18 10:28 AM
 * @Modified:
 */
public class MailResource extends ResourceSupport {
    private String message;
    private String to;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
