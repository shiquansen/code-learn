package com.sbzl.framework.redis.problem.cache;

import com.sbzl.framework.redis.dataType.RedisTemplateForString;

import javax.annotation.Resource;

/**
 * 缓存击穿
 * @param <T>
 */
public class Breakdown<T> {


    @Resource
    private RedisTemplateForString<T> redisTemplateForString;



    //设置热点数据永远不过期。
    //加互斥锁
}
