package com.sbzl.framework.rabbitmq.service;

public interface PutService {

    void putString(String args);

    void createDirect(String name);
}
