package com.sbzl.framework.redis.dataType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * String常用操作
 * @param <T>
 */
@Service
public class RedisTemplateForString<T> {

    @Autowired
    private RedisTemplate<String, T> redisTemplate;

    public T getAndSet(String key, T value){
        return (T)redisTemplate.opsForValue().getAndSet(key, value);
    }

    public void set(String key, T value){
        redisTemplate.opsForValue().set(key, value);
    }

    public T get(String key){
        return (T)redisTemplate.opsForValue().get(key);
    }


    public boolean setNx(String k, T value, Long time, TimeUnit timeUnit){
        return redisTemplate.opsForValue().setIfAbsent(k, value, time, timeUnit);
    }


//    public void setNx(){
//        redisTemplate.opsForValue().getAndSet()
//        redisTemplate.opsForValue().get() 包含setex
//        redisTemplate.opsForValue().decrement()/increment()
//        redisTemplate.opsForValue().size()
//    }






}
