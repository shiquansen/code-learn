package com.sbzl.framework.redis.dataType;

import com.sbzl.framework.redis.base.domain.City;
import com.sbzl.framework.redis.base.service.CityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StringTest {
    @Autowired
    CityService cityService;

    @Autowired
    RedisTemplateForString cityRedisTemplateForString;

    @Test
    void contextLoads() {
        City city = cityService.findCityById(1L);
        city.setCityName("123456");
        System.out.println(cityRedisTemplateForString.size("city_1"));
    }
}
