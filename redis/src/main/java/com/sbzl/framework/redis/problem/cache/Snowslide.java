package com.sbzl.framework.redis.problem.cache;

import com.sbzl.framework.redis.dataType.RedisTemplateForString;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * 缓存雪崩
 */
public class Snowslide<T> {

    @Resource
    private RedisTemplateForString<T> redisTemplateForString;

}
