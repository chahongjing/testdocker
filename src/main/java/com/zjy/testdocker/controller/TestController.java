package com.zjy.testdocker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/index")
    public Object index() {
        return "准备就绪！";
    }
}
