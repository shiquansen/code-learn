package com.sbzl.framework.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

//@Configuration
//public class UserLoginConfig {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean("jdbcUserDetailsManager")
//    public JdbcUserDetailsManager jdbcUserDetailsManager(){
//        return new JdbcUserDetailsManager(dataSource);
//    }
//
//}
