package com.nationalchip.iot.security.audit;

import com.nationalchip.iot.data.model.User;
import com.nationalchip.iot.tenancy.ITenantAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/25/18 3:52 PM
 * @Modified:
 */
public class SecurityContextTenantAware implements ITenantAware {
    @Override
    public String getCurrentTenant() {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() != null) {
            final Object principal = context.getAuthentication().getPrincipal();
            if (principal instanceof User) {
                return ((User) principal).getTenant();
            }
        }
        return null;
    }
}
