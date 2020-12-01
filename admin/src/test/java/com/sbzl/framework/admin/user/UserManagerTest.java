package com.sbzl.framework.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserManagerTest {

    @Autowired
    JdbcUserDetailsManager jdbcUserDetailsManager;

    @Test
    void contextLoads() {
        String groupName = "测试";
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        GrantedAuthority grantedAuthority1 = new SimpleGrantedAuthority("校长");
        GrantedAuthority grantedAuthority2 = new SimpleGrantedAuthority("主任");
        GrantedAuthority grantedAuthority3 = new SimpleGrantedAuthority("老师");
        GrantedAuthority grantedAuthority4 = new SimpleGrantedAuthority("学生");
        grantedAuthorityList.add(grantedAuthority1);
        grantedAuthorityList.add(grantedAuthority2);
        grantedAuthorityList.add(grantedAuthority3);
        grantedAuthorityList.add(grantedAuthority4);


        jdbcUserDetailsManager.createGroup(groupName, grantedAuthorityList);

    }
}
