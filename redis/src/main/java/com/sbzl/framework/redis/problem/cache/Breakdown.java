package com.sbzl.framework.redis.problem.cache;

import com.sbzl.framework.redis.dataType.RedisTemplateForString;

import javax.annotation.Resource;

public class Breakdown<T> {


    @Resource
    private RedisTemplateForString<T> redisTemplateForString;
}
