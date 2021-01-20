package com.sbzl.framework.localcache.controller;

import com.sbzl.framework.localcache.cache.SelectCache;
import com.sbzl.framework.localcache.cache.UpdateCache;
import com.sbzl.framework.localcache.cache.User;
import com.sbzl.framework.localcache.cache.UserFactory;
import com.sbzl.framework.localcache.utils.SpringBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    SelectCache selectCache;

    @Resource
    CacheManager cacheManager;

    @Resource
    UpdateCache updateCache;

    @GetMapping("getUser")
    public User getUser(Integer id){
        return selectCache.getUser(id);
    }

    @GetMapping("updateUser")
    public User getUser(User user){
        return updateCache.update(user);
    }

    @GetMapping("cache")
    public Object getCache(){
        return cacheManager.getCache("userMap");

    }


}
