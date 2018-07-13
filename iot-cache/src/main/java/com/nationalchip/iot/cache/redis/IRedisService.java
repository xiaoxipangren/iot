package com.nationalchip.iot.cache.redis;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 7/3/18 9:58 AM
 * @Modified:
 */
public interface IRedisService {
    void set(String key,Object value);
    void set(String key,Object value,long timeout);
    void set(String key, Object value, long timeout, TimeUnit unit);

    Object get(String key);

    boolean hasKey(String key);
    void delete(String key);
}
