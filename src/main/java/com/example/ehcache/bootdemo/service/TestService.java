package com.example.ehcache.bootdemo.service;

/**
 * @author QiuPengJun
 * @version 1.0
 * @date 2020/7/29 16:20
 */
public interface TestService {
    String testRedis(String key);
    String testEhcache(String key);
    String testTwo(String key);
}
