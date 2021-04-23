package com.zjy.testdocker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试类
 */
@RestController
public class TestController {
    Logger log = LoggerFactory.getLogger(TestController.class);

    /**
     * 首页
     * @param a 测试参数
     * @return
     */
    @GetMapping("/index")
    public String index(Long a) {
        log.info("现在访问服务的index请求了！");
        return "准备就绪1！";
    }
}
