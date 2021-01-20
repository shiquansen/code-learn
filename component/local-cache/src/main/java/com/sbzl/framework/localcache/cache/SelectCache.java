package com.sbzl.framework.localcache.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SelectCache {

    private static final Logger logger = LoggerFactory.getLogger("SelectCache");

    @Resource
    UserFactory userFactory;

    @Cacheable(cacheNames = "userMap", key = "#id")
    public User getUser(Integer id){
        logger.info("查询用户 id{}", id);
        return userFactory.selectUser(id);
    }
}
