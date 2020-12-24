package com.sbzl.framework.admin.config;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
public class InitConfig implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("项目初始化成功  ！！！！！！！！！！！！ ");
    }
}
