package com.nationalchip.iot.message.captcha;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/4/18 2:38 PM
 * @Modified:
 */
public class CaptchaBuilder implements ICaptchaBuilder {
    
    private String value;
    private String action;
    private String target;


    @Override
    public ICaptchaBuilder value(String value) {
        this.value = value;
        return self();
    }

    @Override
    public ICaptchaBuilder action(String action) {
        this.action = action;
        return self();
    }

    @Override
    public ICaptchaBuilder target(String target) {
        this.target = target;
        return self();
    }



    @Override
    public ICaptcha build() {
        Captcha captcha = new Captcha();
        captcha.setValue(value);
        captcha.setKey(String.join(SEPARATOR,action,target));
        captcha.setDate(LocalDateTime.now());
        captcha.setTarget(target);
        captcha.setAction(action);

        CaptchaAction action = CaptchaAction.from(this.action);
        captcha.setImaged(action!=null?action.isImaged():false);


        return captcha;
    }

    private ICaptchaBuilder self(){
        return this;
    }
}
