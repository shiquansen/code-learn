package com.sbzl.framework.rabbitmq;

import com.sbzl.framework.rabbitmq.simple.Sender;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    Sender sender;

    /**
     * 简单的队列
     */
    @Test
    void simple() throws InterruptedException {
        while(true){
            sender.sender("hello world");
            Thread.sleep(1000);
        }
    }

    @Autowired
    com.sbzl.framework.rabbitmq.mq_type.direct.provider.Sender directSender;
    @Test
    void direct() throws InterruptedException {
        while(true){
            directSender.send("hello world");
            Thread.sleep(1000);
        }
    }

    @Autowired
    com.sbzl.framework.rabbitmq.mq_type.fanout.provider.Sender fanoutSender;
    @Test
    void fanout() throws InterruptedException {
        while(true){
            fanoutSender.send("hello world");
            Thread.sleep(1000);
        }
    }

    @Autowired
    com.sbzl.framework.rabbitmq.mq_type.topic.provider.OrderSender orderSender;
    com.sbzl.framework.rabbitmq.mq_type.topic.provider.ProductSender productSender;
    com.sbzl.framework.rabbitmq.mq_type.topic.provider.UserSender userSender;
    @Test
    void topic() throws InterruptedException {
//        while(true){
            orderSender.send("orderSender :hello world");
//            productSender.send("productSender :hello world");
//            userSender.send("productSender :hello world");
//            Thread.sleep(1000);
//        }
    }

    @Autowired
    com.sbzl.framework.rabbitmq.function.coupling.provider.Sender couplingSender;
    @Test
    void couplingSender() throws InterruptedException {
        while(true){
            couplingSender.send("hello world");
            Thread.sleep(1000);
        }
    }

    @Autowired
    com.sbzl.framework.rabbitmq.function.persistence.provider.Sender persistenceSender;
    @Test
    void persistenceSender() throws InterruptedException {
        int flag = 0;
        while(true){
            flag++;
            persistenceSender.send(flag +"hello world");
            Thread.sleep(1000);
        }
    }

}
