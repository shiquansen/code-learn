package com.sbzl.framework.mongo.service.impl;

import com.sbzl.framework.mongo.domain.School;
import com.sbzl.framework.mongo.service.MongoOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoOperationServiceImpl implements MongoOperationService {

    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public void createCollection(Object obj) {
        String s = obj.toString();
        mongoTemplate.createCollection(s);
        mongoTemplate.dropCollection(s);
    }

    @Override
    public void createDocument(Object obj) {

    }

    //**********************************************************

    @Override
    public void save(School student) {
        mongoTemplate.save(student);
    }

    @Override
    public void update(School student) {
        Query query = new Query(Criteria.where("id").is(student.getId()));
        Update update = new Update();
        update.set("name",student.getName());
        update.set("code",student.getCode());
        mongoTemplate.updateFirst(query,update,School.class);
    }

    /**
     * 查询所有信息
     * @return
     */
    @Override
    public List<School> findAll() {
        return mongoTemplate.findAll(School.class);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Integer id) {
        School byId = mongoTemplate.findById(id,School.class);
        assert byId != null;
        mongoTemplate.remove(byId);
    }

}
