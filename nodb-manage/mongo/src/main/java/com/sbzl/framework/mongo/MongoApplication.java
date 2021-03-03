package com.sbzl.framework.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class MongoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MongoApplication.class, args);
        String[] names = run.getBeanDefinitionNames();
        Arrays.stream(names).forEach(System.out::println);
    }

}
