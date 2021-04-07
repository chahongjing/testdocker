package com.zjy.testdocker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    Logger log = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/index")
    public Object index() {
        log.info("现在访问服务的index请求了！");
        return "准备就绪1！";
    }
}
