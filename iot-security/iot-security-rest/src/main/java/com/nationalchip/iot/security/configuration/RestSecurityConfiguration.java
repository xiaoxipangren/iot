package com.nationalchip.iot.security.configuration;

import com.nationalchip.iot.annotation.AutoLogger;
import com.nationalchip.iot.cache.redis.IRedisService;
import com.nationalchip.iot.security.authentication.AccountTypeAuthenticationProvider;
import com.nationalchip.iot.security.authentication.JwtAuthenticationFilter;
import com.nationalchip.iot.security.jwt.IJwtProvider;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

import static com.nationalchip.iot.security.configuration.RestMappingConstant.*;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 2/26/18 9:07 AM
 * @Modified:
 */
@Configuration
@ComponentScan(basePackages = "com.nationalchip.iot")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启pre/postAuthorize注解
@PropertySource("classpath:iot-security-rest.properties")
@EnableConfigurationProperties(RestSecurityProperty.class)
public class RestSecurityConfiguration extends WebSecurityConfigurerAdapter {


    private static final String ASSET_MAPPING = REST_BASE_MAPPING + REST_ASSET_MAPPING;
    private static final String ASSET_DOWNLOAD_MAPPING = ASSET_MAPPING + REST_DOWNLOAD_ACTION;
    private static final String REST_GENERIC_MAPPING = REST_BASE_MAPPING + "/**";


    @AutoLogger
    private Logger logger;


    @Autowired
    private IJwtProvider jwtProvider;


    @Autowired
    private IRedisService redisService;

    @Autowired
    private RestSecurityProperty securityProperty;



    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtProvider, redisService, securityProperty);

        http.cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() //禁用session
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,mapping(REST_CAPTCHA_MAPPING)).permitAll()
                .antMatchers(HttpMethod.GET, REST_GENERIC_MAPPING).permitAll()
                .antMatchers(HttpMethod.HEAD, REST_GENERIC_MAPPING).permitAll()
                .antMatchers(REST_GENERIC_MAPPING).authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        //web.ignoring 一般用来处理静态资源，如css/js/html等

//        web.ignoring()
//                .antMatchers(HttpMethod.POST, mapping(REST_CAPTCHA_MAPPING))
//                .antMatchers(HttpMethod.GET,mapping(REST_CAPTCHA_MAPPING));

    }

    private String mapping(String mapping) {
        return REST_BASE_MAPPING + mapping;
    }

    private String mapId(String mapping) {
        return mapping(mapping) + REST_ID_MAPPING;
    }


}
