package com.sbzl.framework.rabbitmq.simple;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {


    @Autowired
    AmqpTemplate amqpTemplate;

    /**
     * 发送消息
     * @param msg
     */
    public void sender(String msg){
        amqpTemplate.convertAndSend("hello-queue", msg);
    }
}
