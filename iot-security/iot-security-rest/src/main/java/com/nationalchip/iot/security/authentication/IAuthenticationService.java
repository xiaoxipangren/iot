package com.nationalchip.iot.security.authentication;

import com.nationalchip.iot.security.authority.AuthorityExpression;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import java.util.function.Supplier;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/11/18 1:04 PM
 * @Modified:
 */
public interface IAuthenticationService {
    @PreAuthorize(AuthorityExpression.HAS_AUTH_LOGIN)
    Authentication authenticate(AccountTypeAuthenticationToken token);

    @PreAuthorize("isAuthenticated()")
    void unAuthenticate();

    @PreAuthorize("#principal==authentication.name")
    <T> T runOnce(String principal,Supplier<T> supplier);
}
