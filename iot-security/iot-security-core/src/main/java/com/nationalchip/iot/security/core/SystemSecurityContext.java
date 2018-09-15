package com.nationalchip.iot.security.core;

import com.nationalchip.iot.annotation.AutoLogger;
import com.nationalchip.iot.context.ISecurityContext;
import com.nationalchip.iot.security.authority.Authority;
import com.nationalchip.iot.tenancy.ITenantAware;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/5/18 9:09 AM
 * @Modified:
 */
@Component
public class SystemSecurityContext implements ISecurityContext{

    @AutoLogger
    private  Logger logger;

    @Autowired
    private ITenantAware tenantAware;


    public SystemSecurityContext() {
    }


    @SuppressWarnings("squid:S2221")
    public <T> T runAsSystem(final Callable<T> callable) {

        return runAsSystemAsTenant(callable, tenantAware.getCurrentTenant());
    }

    public <T> T runAsSystemAsTenant(final Callable<T> callable, final String tenant) {
        final SecurityContext oldContext = SecurityContextHolder.getContext();
        try {
            logger.debug("entering system code execution");
            return tenantAware.runAs(tenant, () -> {
                try {
                    setSystemContext(SecurityContextHolder.getContext());
                    return callable.call();

                } catch (final RuntimeException e) {
                    throw e;
                } catch (final Exception e) {
                    throw new RuntimeException(e);
                }
            });

        } finally {
            SecurityContextHolder.setContext(oldContext);
            logger.debug("leaving system code execution");
        }
    }

    /**
     * @return {@code true} if the current running code is running as system
     *         code block.
     */
    public boolean isCurrentThreadSystemCode() {
        return SecurityContextHolder.getContext().getAuthentication() instanceof SystemCodeAuthentication;
    }

    private static void setSystemContext(final SecurityContext oldContext) {
        final Authentication oldAuthentication = oldContext.getAuthentication();
        final SecurityContextImpl securityContextImpl = new SecurityContextImpl();
        securityContextImpl.setAuthentication(new SystemCodeAuthentication(oldAuthentication));
        SecurityContextHolder.setContext(securityContextImpl);
    }


    public static final class SystemCodeAuthentication implements Authentication {

        private static final long serialVersionUID = 1L;
        private static final List<? extends GrantedAuthority> AUTHORITIES = Collections
                .singletonList(new SimpleGrantedAuthority(Authority.SYSTEM));
        private final Authentication oldAuthentication;

        private SystemCodeAuthentication(final Authentication oldAuthentication) {
            this.oldAuthentication = oldAuthentication;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return AUTHORITIES;
        }

        @Override
        public Object getCredentials() {
            return oldAuthentication != null ? oldAuthentication.getCredentials() : null;
        }

        @Override
        public Object getDetails() {
            return oldAuthentication != null ? oldAuthentication.getDetails() : null;
        }

        @Override
        public Object getPrincipal() {
            return oldAuthentication != null ? oldAuthentication.getPrincipal() : null;
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public void setAuthenticated(final boolean isAuthenticated) {
            // not needed
        }
    }
}

