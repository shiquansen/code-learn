package com.sbzl.framework.mongo;

import com.sbzl.framework.mongo.domain.School;
import com.sbzl.framework.mongo.service.MongoOperationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class MongoApplicationTests {

    @Autowired
    MongoOperationService mongoOperationService;

    @Test
    void save() {
        School school = new School();
        for(int i = 2; i < 100; i++){
            school.setId(i);
            school.setCode(Math.floor(Math.random() * 100) + "");
            school.setName(UUID.randomUUID().toString());
            mongoOperationService.save(school);
        }
    }

    @Test
    void update() {
        School school = new School();
        school.setId(1);
        school.setCode("l love you");
        school.setName("jxm");
        mongoOperationService.update(school);
    }

    @Test
    void findAll() {
        System.out.println(mongoOperationService.findAll().size());
    }

    @Test
    void delete() {
        mongoOperationService.delete(1);
    }



}
