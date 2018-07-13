package com.nationalchip.iot.rest.resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/4/18 4:06 PM
 * @Modified:
 */
public class CaptchaResponse extends ResourceSupport {


    //生成日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private String action;

    private String target;


    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

}
