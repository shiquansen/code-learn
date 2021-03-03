package com.sbzl.framework.elasticsearch.service;


import java.util.List;

public interface ElasticSearchOperationService {

    void createCollection(Object obj);

    void createDocument(Object obj);

    void save(School student);

    void update(School student);

    List<School> findAll();

    void delete(Integer id);
}
