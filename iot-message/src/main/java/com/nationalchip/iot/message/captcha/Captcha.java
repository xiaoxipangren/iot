package com.nationalchip.iot.message.captcha;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/3/18 7:52 PM
 * @Modified:
 */
public class Captcha implements ICaptcha {
    private String value;
    private LocalDateTime date;
    private String key;
    private boolean imaged;
    private String action;
    private String target;

    @Override
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = toLower(key);
    }

    public Captcha(){
        date = LocalDateTime.now();
        imaged=false;
    }

    public Captcha(String value){
        this();
        setValue(value);
    }

    public Captcha(String key,String value){
        this(value);
        setValue(key);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = toLower(value);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setImaged(boolean imaged) {
        this.imaged = imaged;
    }

    @Override
    public boolean isImaged() {
        return imaged;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


    private String  toLower(String value){
        return value==null?null:value.toLowerCase();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null || !(obj instanceof ICaptcha))
            return false;
        ICaptcha captcha = (ICaptcha)obj;

        if(getKey()==null || getValue() == null)
            return false;

        return  getValue().equals(captcha.getValue())
                && getKey().equals(captcha.getKey());
    }
}
