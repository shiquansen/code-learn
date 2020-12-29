package com.sbzl.framework.redis.reidsopration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisTemplateForString {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;




}
