package com.sbzl.framework.multi.datasource;

import com.sbzl.framework.multi.datasource.dao.cluster.StudentDao;
import com.sbzl.framework.multi.datasource.dao.master.UserDao;
import com.sbzl.framework.multi.datasource.pojo.Student;
import com.sbzl.framework.multi.datasource.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MultiDatasourceApplicationTests {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() throws Exception {
        studentDao.insert(new Student());
        userDao.insert(new User());
    }

}
