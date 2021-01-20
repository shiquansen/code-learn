package com.sbzl.framework.redis.dataType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

/**
 * 常用的大概就这么些
 * @param <T>
 */
public class RedisTemplateForCommon<T> {
    @Autowired
    private RedisTemplate<String, T> redisTemplate;

    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    public boolean delete(String key){
        return redisTemplate.delete(key);
    }

    public Set<String> keys(String pattern){
        return redisTemplate.keys(pattern);
    }
}
