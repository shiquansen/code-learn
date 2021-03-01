package com.sbzl.framework.mongo.service;

import com.sbzl.framework.mongo.domain.School;

import java.util.List;

public interface MongoOperationService {

    void createCollection(Object obj);

    void createDocument(Object obj);

    void save(School student);

    void update(School student);

    List<School> findAll();

    void delete(Integer id);
}
