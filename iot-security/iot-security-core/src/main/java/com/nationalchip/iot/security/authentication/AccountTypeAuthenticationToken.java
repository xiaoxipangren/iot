package com.nationalchip.iot.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/13/18 3:26 PM
 * @Modified:
 */
public class AccountTypeAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private AccountTypeRequestClient client;

    public AccountTypeRequestClient getClient() {
        return client;
    }

    public void setClient(AccountTypeRequestClient client) {
        this.client = client;
    }

    public AccountTypeAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public AccountTypeAuthenticationToken(Object principal, Object credentials,AccountTypeRequestClient client){
        this(principal,credentials);
        this.client=client;
    }


}
