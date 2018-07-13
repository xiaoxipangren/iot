package com.nationalchip.iot.rest.resource;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/3/18 4:18 PM
 * @Modified:
 */
public class CaptchaRequest extends BaseRequest {

    //验证码用途
    private String action;

    //验证码作用目标，如用户名/email/phone等
    private String target;

    //验证码发送地址，如邮箱/手机等，为空则生成图片验证码
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //验证码值
    private String code;


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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
