package com.example.ehcache.bootdemo.controller;

import com.example.ehcache.bootdemo.service.TestService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringWriter;
import java.util.HashMap;

/**
 * @author QiuPengJun
 * @version 1.0
 * @date 2020/7/29 17:03
 */
@RestController
public class testController {
    @Autowired
    TestService testService;
    @RequestMapping("/test.do")
    public String test(@RequestParam int type) throws InterruptedException {
        switch (type){
            case 1:
                testService.testRedis("testCache1");
                break;
            case 2:
                testService.testEhcache("testCache2");
                break;
            case 3:
                testService.testTwo("testCache3");
                break;
            default:
                break;
        }
        return "ok";

    }
    @RequestMapping("/test2.do")
    public String test23(@RequestParam String p3,@RequestParam String p4) throws InterruptedException {
        return "12312123123";
    }

}
