package com.nationalchip.iot.rest.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/20/18 4:59 PM
 * @Modified:
 */

@ConfigurationProperties("iot.rest")
public class RestProperty {

    private String validateMail;


    private String resetpwdMail;


    private int validationExpiration;

    public String getValidateMail() {
        return validateMail;
    }

    public void setValidateMail(String validateMail) {
        this.validateMail = validateMail;
    }

    public String getResetpwdMail() {
        return resetpwdMail;
    }

    public void setResetpwdMail(String resetpwdMail) {
        this.resetpwdMail = resetpwdMail;
    }

    public int getValidationExpiration() {
        return validationExpiration;
    }

    public void setValidationExpiration(int validationExpiration) {
        this.validationExpiration = validationExpiration;
    }
}
