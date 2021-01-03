package com.sbzl.framework.redis.problem.cache;

import com.sbzl.framework.redis.dataType.RedisTemplateForString;

import javax.annotation.Resource;

/**
 * 缓存穿透
 */
public class Penetrate<T> {

    @Resource
    private RedisTemplateForString<T> redisTemplateForString;
}
