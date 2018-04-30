package com.nationalchip.iot.security.core;

import com.nationalchip.iot.data.model.auth.Role;
import com.nationalchip.iot.data.model.auth.User;
import com.nationalchip.iot.security.authentication.AuthenticationDetails;
import com.nationalchip.iot.tenancy.ITenantAware;
import com.nationalchip.iot.tenancy.TenantRunner;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;

import javax.jws.soap.SOAPBinding;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/25/18 3:52 PM
 * @Modified:
 */

@Component
public class SecurityContextTenantAware implements ITenantAware {

    @Override
    public String getCurrentTenant() {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() != null) {
            Authentication authentication = context.getAuthentication();
            final Object principal = authentication.getPrincipal();
            if (principal instanceof String) {
                return (String)principal;
            }
            if(authentication.getDetails() instanceof AuthenticationDetails){
                AuthenticationDetails details = (AuthenticationDetails)authentication.getDetails();
                return details.getTenant();
            }
        }
        return null;
    }

    @Override
    public <T> T runAs(String tenant, TenantRunner<T> runner) {
        final SecurityContext originalContext = SecurityContextHolder.getContext();
        try {
            SecurityContextHolder.setContext(buildSecurityContext(tenant));
            return runner.run();
        } finally {
            SecurityContextHolder.setContext(originalContext);
        }
    }

    private static SecurityContext buildSecurityContext(final String tenant) {
        final SecurityContextImpl securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(
                new AuthenticationDelegate(SecurityContextHolder.getContext().getAuthentication(), tenant));
        return securityContext;
    }

    private static final class AuthenticationDelegate implements Authentication {
        private static final long serialVersionUID = 1L;

        private static final Collection<? extends GrantedAuthority> SYSTEM_AUTHORITIES = Arrays
                .asList(Role.SystemRole());
        private final Authentication delegate;

        private final User systemPrincipal;


        private AuthenticationDelegate(final Authentication delegate, final String tenant) {
            this.delegate = delegate;
            this.systemPrincipal = User.SystemUser(tenant);
        }

        @Override
        public boolean equals(final Object another) {
            if (delegate != null) {
                return delegate.equals(another);
            } else if (another == null) {
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return (delegate != null) ? delegate.toString() : null;
        }

        @Override
        public int hashCode() {
            return (delegate != null) ? delegate.hashCode() : -1;
        }

        @Override
        public String getName() {
            return (delegate != null) ? delegate.getName() : null;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return (delegate != null) ? delegate.getAuthorities() : Collections.emptyList();
        }

        @Override
        public Object getCredentials() {
            return (delegate != null) ? delegate.getCredentials() : null;
        }

        @Override
        public Object getDetails() {
            return this;
        }

        @Override
        public Object getPrincipal() {
            return systemPrincipal;
        }

        @Override
        public boolean isAuthenticated() {
            return (delegate != null) ? delegate.isAuthenticated() : true;
        }

        @Override
        public void setAuthenticated(final boolean isAuthenticated) {
            if (delegate == null) {
                return;
            }
            delegate.setAuthenticated(isAuthenticated);
        }
    }


}
