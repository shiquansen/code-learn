package com.sbzl.framework.redis;

import com.sbzl.framework.redis.base.mapper.CityMapper;
import com.sbzl.framework.redis.base.service.CityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    CityService cityService;

    @Test
    void contextLoads() {
        System.out.println(cityService.findCityById(1L));
    }

}
