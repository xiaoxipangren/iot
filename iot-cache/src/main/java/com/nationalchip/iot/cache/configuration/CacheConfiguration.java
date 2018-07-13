package com.nationalchip.iot.cache.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/19/18 11:04 AM
 * @Modified:
 *
 * TODO:
 * 组合RedisTemplate和KeyProvider生成一个CacheService
 */
@PropertySource("classpath:iot-cache-default.properties")
@EnableCaching
@Configuration
@EnableConfigurationProperties(CacheProperty.class)
public class CacheConfiguration extends CachingConfigurerSupport{


    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        return rcm;
    }

//    @Bean
//    public KeyGenerator keyGenerator() {
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object target, Method method, Object... params) {
//                StringBuilder sb = new StringBuilder();
//                String[] value = new String[1];
//                Cacheable cacheable = method.getAnnotation(Cacheable.class);
//                if (cacheable != null) {
//                    value = cacheable.value();
//                }
//                CachePut cachePut = method.getAnnotation(CachePut.class);
//                if (cachePut != null) {
//                    value = cachePut.value();
//                }
//                CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
//                if (cacheEvict != null) {
//                    value = cacheEvict.value();
//                }
//                sb.append(value[0]);
//                for (Object obj : params) {
//                    sb.append(":")
//                            .append(obj.toString());
//                }
//                return sb.toString();
//            }
//        };
//    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.registerModule(new JavaTimeModule());
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }



}