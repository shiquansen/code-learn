package com.sbzl.framework.admin.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/web")
public class AdminWeb {

    @GetMapping(value = "/hello")
    public String test(){
        return "hello world, hello";
    }

    @GetMapping(value = "/index")
    public String index(){
        return "hello world, index";
    }
}
