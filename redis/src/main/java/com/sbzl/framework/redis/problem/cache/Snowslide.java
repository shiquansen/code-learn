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

    /**
     * 缓存数据的过期时间设置随机，防止同一时间大量数据过期现象发生。
     */

    /**
     *  如果缓存数据库是分布式部署，将热点数据均匀分布在不同搞得缓存数据库中。
     */

}
