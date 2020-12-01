package com.sbzl.framework.admin.web;

import com.sbzl.framework.admin.web.path.AdminPath;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminWeb {

    @GetMapping(value = AdminPath.ADMIN_TEST)
    public String test(){
        return "hello world";
    }
}
