package com.sbzl.framework.redis.lua;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public class LuaService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    private DefaultRedisScript<Long> script;
    private static final String IPACCESS_KEY_PREX = "ipaccess_";

    @PostConstruct
    private void init(){
        script = new DefaultRedisScript<Long>();
        script.setResultType(Long.class);
        script.setScriptSource(new ResourceScriptSource(new
                ClassPathResource("ipaccess.lua")));
    }

    public boolean exec(String ip) {
        List<String> keys = new ArrayList<>();
        keys.add(IPACCESS_KEY_PREX + ip);
        return redisTemplate.execute(script, keys, 10, 20) == 1 ? true : false;
    }

}
