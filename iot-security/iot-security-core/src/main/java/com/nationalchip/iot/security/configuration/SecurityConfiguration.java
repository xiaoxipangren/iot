package com.nationalchip.iot.security.configuration;

import com.nationalchip.iot.annotation.AutoLogger;
import com.nationalchip.iot.context.ISecurityContext;
import com.nationalchip.iot.security.audit.SpringSecurityAuditorAware;
import com.nationalchip.iot.security.core.SecurityContextTenantAware;
import com.nationalchip.iot.security.core.SystemSecurityContext;
import com.nationalchip.iot.tenancy.ITenantAware;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/26/18 9:07 AM
 * @Modified:
 */
@Configuration
public class SecurityConfiguration {

    @AutoLogger
    private Logger logger;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @ConditionalOnMissingBean
    public AuditorAware<String> auditorAware() {
        return new SpringSecurityAuditorAware();
    }

    @Bean
    public ITenantAware tenantAware(){
        return new SecurityContextTenantAware();
    }

    @Bean
    public ISecurityContext systemSecurityContext(){
        return new SystemSecurityContext(tenantAware());
    }


}
