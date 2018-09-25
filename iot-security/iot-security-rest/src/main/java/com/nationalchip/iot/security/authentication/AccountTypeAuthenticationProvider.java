package com.nationalchip.iot.security.authentication;

import com.nationalchip.iot.data.model.auth.IConsumer;
import com.nationalchip.iot.data.model.auth.IDeveloper;
import com.nationalchip.iot.data.model.auth.IUser;
import com.nationalchip.iot.data.model.auth.IAdmin;
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

        if(!(userDetails instanceof IUser))
            throw new AccountTypeNotMatchedException();

        AccountTypeRequestClient client = token.getClient();

        IUser user = (IUser) userDetails;

        if(!user.isMatch(token.getClient().getSource())){
            throw new AccountTypeNotMatchedException();
        }


        if (isAdmin(user)){
            IAdmin admin = (IAdmin) user;

            if(admin.isRestrictIp()){
                if(admin.getIp() != client.getIp() && admin.getIp().equalsIgnoreCase(client.getIp()) ){
                    throw new AccountTypeNotMatchedException();
                }
            }


            if(admin.isRestrictMac()){
                //待实现
            }


        }


    }

    private boolean isAdmin(UserDetails userDetails){

        return  userDetails instanceof IAdmin;
    }

    private boolean isConsumer(UserDetails userDetails){

        return  userDetails instanceof IConsumer;
    }

    private boolean isDeveloper(UserDetails userDetails){

        return  userDetails instanceof IDeveloper;
    }

}
