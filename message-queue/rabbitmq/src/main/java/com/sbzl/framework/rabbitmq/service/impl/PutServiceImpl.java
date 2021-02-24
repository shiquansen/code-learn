package com.sbzl.framework.rabbitmq.service.impl;

import com.sbzl.framework.rabbitmq.service.PutService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PutServiceImpl implements PutService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public void putString(String args) {

    }

    @Override
    public void createDirect(String name) {
        DirectExchange directExchange = new DirectExchange(name);

    }
}
