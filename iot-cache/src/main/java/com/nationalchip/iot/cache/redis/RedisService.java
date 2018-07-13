package com.nationalchip.iot.cache.redis;

import com.nationalchip.iot.cache.configuration.CacheProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/3/18 10:04 AM
 * @Modified:
 */
@Component
public class RedisService implements IRedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CacheProperty cacheProperty;

    @Override
    public void set(String key, Object value) {
        set(key,value,cacheProperty.getInterval());
    }

    @Override
    public void set(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key,value,timeout,TimeUnit.SECONDS);
    }

    @Override
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key,value,timeout,unit);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
