package com.sbzl.framework.rabbitmq.simple;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {


    @Autowired
    AmqpTemplate amqpTemplate;

    /**
     * 接收消息的方法
     * @param msg
     */
    @RabbitListener(queues = "hello-queue")
    public void process(String msg){
        System.out.println("receiver: "+msg);
    }
}
