package com.sbzl.framework.localcache.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
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

    @CachePut(cacheNames = "userMap",key = "#user.id")
    public User insert(User user){
        logger.info("新增用户 id:{}", user.getId());
        if(userFactory.addUser(user) == 1){
            return user;
        }
        return null;
    }

    @CacheEvict(cacheNames = "userMap",key = "#id")
    public Integer delete(Integer id){
        logger.info("删除用户 id:{}", id);
        if(userFactory.deleteUser(id) == 1){
            return 1;
        }
        return 0;
    }

}
