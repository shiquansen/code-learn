package com.sbzl.framework.admin;

import com.sbzl.framework.admin.system.mapper.UserMapper;
import com.sbzl.framework.admin.system.model.User;
import com.sbzl.framework.admin.system.service.MyUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

@SpringBootTest
class AdminApplicationTests {

    @Autowired
    MyUserService userService;

//    @Autowired
//    DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {

        userService.insert("admin","admin");
//        System.out.println(dataSource.getConnection());
    }

}
