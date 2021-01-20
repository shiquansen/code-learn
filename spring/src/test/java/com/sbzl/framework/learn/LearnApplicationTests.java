package com.sbzl.framework.learn;

import com.sbzl.framework.learn.readClassFile.ReadClassService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class LearnApplicationTests {


    @Autowired
    ReadClassService readClassService;
    @Test
    void contextLoads() throws IOException {
        readClassService.read("test.txt");
    }

}
