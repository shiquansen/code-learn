package com.sbzl.framework.localcache.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UpdateCache {

    private static final Logger logger = LoggerFactory.getLogger("UpdateCache");

    @Resource
    UserFactory userFactory;

    @CachePut(cacheNames = "userMap",key = "#user.id")
    public User update(User user){
        logger.info("更新用户 id:{}", user.getId());
        if(userFactory.updateUser(user) == 1){
            return user;
        }
        return null;
    }

}
