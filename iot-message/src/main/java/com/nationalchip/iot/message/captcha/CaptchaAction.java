package com.nationalchip.iot.message.captcha;

import com.nationalchip.iot.security.authority.Authority;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/5/18 10:03 AM
 * @Modified:
 */
public enum  CaptchaAction {

    LOGIN(Authority.AUTH_LOGIN,true),
    REGISTER(Authority.AUTH_REGISTER,false),
    RESET_PASSWORD(Authority.AUTH_RESET_PASSWORD,false);

    private String name;
    private boolean imaged;

    CaptchaAction(String name, boolean imaged){
        this.name=name;
        this.imaged=imaged;
    }

    public boolean isImaged() {
        return imaged;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImaged(boolean imaged) {
        this.imaged = imaged;
    }

    public static CaptchaAction from(String action){
        for(CaptchaAction captchaAction : CaptchaAction.values()){
            if(captchaAction.name.toUpperCase().equalsIgnoreCase(action.toUpperCase())){
                return captchaAction;
            }
        }

        return null;
    }
}
