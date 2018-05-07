package com.nationalchip.iot.security.configuration;

import com.nationalchip.iot.annotation.AutoLogger;
import com.nationalchip.iot.security.authentication.JwtAuthenticationFilter;
import com.nationalchip.iot.security.provider.IJwtProvider;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/26/18 9:07 AM
 * @Modified:
 */
@Configuration
@ComponentScan(basePackages = "com.nationalchip.iot")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)//prePostEnabled将父bean导入到子bean
@PropertySource("classpath:iot-security-default.properties")
@EnableConfigurationProperties(RestSecurityProperty.class)
public class RestSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String AUTH_MAPPING=RestConstant.REST_BASE_MAPPING+RestConstant.REST_AUTH_MAPPING;

    private static final String AUTH_REGISTER_MAPPING=AUTH_MAPPING+RestConstant.REST_REGISTER_ACTION;
    private static final String AUTH_LOGIN_MAPPING=AUTH_MAPPING+RestConstant.REST_LOGIN_ACTION;
    private static final String AUTH_VALIDATE_MAPPING=AUTH_MAPPING+RestConstant.REST_VALIDATE_ACTION;
    private static final String AUTH_EXISTS_MAPPING=AUTH_MAPPING+RestConstant.REST_EXISTS_ACTION+"/**";
    private static final String AUTH_SENDMAIL_MAPPING=AUTH_MAPPING+RestConstant.REST_SENDMAIL_ACTION;
    private static final String AUTH_RESETPWD_MAPPING=AUTH_MAPPING+RestConstant.REST_RESETPWD_ACTION;


    @AutoLogger
    private Logger logger;


    @Autowired
    private IJwtProvider jwtProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(final HttpSecurity http) throws Exception{
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtProvider,redisTemplate);

        http.cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() //禁用session
                .authorizeRequests()
                .antMatchers(AUTH_LOGIN_MAPPING,AUTH_REGISTER_MAPPING,AUTH_VALIDATE_MAPPING,AUTH_SENDMAIL_MAPPING,AUTH_EXISTS_MAPPING,AUTH_RESETPWD_MAPPING).permitAll()
                .antMatchers(RestConstant.REST_BASE_MAPPING+"/**").authenticated().and()
                .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);


    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //注册、登录、发送邮件、验证邮箱无需验证token
        web.ignoring().antMatchers(AUTH_LOGIN_MAPPING,AUTH_REGISTER_MAPPING,AUTH_VALIDATE_MAPPING,AUTH_SENDMAIL_MAPPING,AUTH_EXISTS_MAPPING,AUTH_RESETPWD_MAPPING);
    }


}
