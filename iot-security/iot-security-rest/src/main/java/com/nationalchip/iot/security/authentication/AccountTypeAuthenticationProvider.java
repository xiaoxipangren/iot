package com.nationalchip.iot.security.authentication;

import com.nationalchip.iot.security.exception.AccountTypeNotMatchedException;
import com.nationalchip.iot.security.exception.AuthenticationTokenNotSupportedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/13/18 3:08 PM
 * @Modified:
 */

public class AccountTypeAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);

        if(!(authentication instanceof AccountTypeAuthenticationToken)){
            throw new AuthenticationTokenNotSupportedException();
        }

        AccountTypeAuthenticationToken token = (AccountTypeAuthenticationToken) authentication;

        Class type= token.getType();

        if(!type.isInstance(userDetails)){
            throw new AccountTypeNotMatchedException();
        }


    }
}
