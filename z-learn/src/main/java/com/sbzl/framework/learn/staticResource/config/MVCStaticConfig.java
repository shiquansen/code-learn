package com.sbzl.framework.learn.staticResource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * 静态资源放行后，可以直接通过端口访问
 */
@Configuration
class MVCStaticConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/image/**"
        )
                .addResourceLocations(
                        "classpath:/image/"
                );
    }


}