package com.nationalchip.iot.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/13/18 3:26 PM
 * @Modified:
 */
public class AccountTypeAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private Class type;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public AccountTypeAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public AccountTypeAuthenticationToken(Object principal, Object credentials,Class type){
        this(principal,credentials);
        this.type=type;
    }


}
