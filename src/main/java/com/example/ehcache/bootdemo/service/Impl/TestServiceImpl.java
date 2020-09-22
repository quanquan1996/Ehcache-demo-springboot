package com.example.ehcache.bootdemo.service.Impl;

import com.example.ehcache.bootdemo.config.CacheManagerConfiguration;
import com.example.ehcache.bootdemo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author QiuPengJun
 * @version 1.0
 * @date 2020/7/29 16:23
 */
@Component
public class TestServiceImpl implements TestService {
    public static final String TEST_TWO ="testTwo";
    @Autowired
    TestService testService;

    /**
     * 用redis做方法級別緩存
     * @param key 入參
     * @return 模拟数据库查询结果
     */
    @Cacheable(key = "#key", cacheNames = "120cache",
            cacheManager = CacheManagerConfiguration.CacheManagerNames.REDIS_CACHE_MANAGER)
    @Override
    public String testRedis(String key) {
        System.out.println("redis没有数据,到数据库查询");
        return "testRedis";
    }

    /**
     * 用ehcache做方法級別緩存,内部调用使用redis做缓存的方法，实现二级缓存
     * @param key 入參
     * @return 模拟数据库查询结果
     */
    @Cacheable(key = "#key", cacheNames = "ehCacheTest",
            cacheManager = CacheManagerConfiguration.CacheManagerNames.EHCACHE_CACHE_MANAGER)
    @Override
    public String testTwo(String key) {
        System.out.println("Ehcache 没有数据，到redis查询");
        return testService.testRedis(key);
    }

    @Cacheable(key = "#key", cacheNames = "ehCacheTest",
            cacheManager = CacheManagerConfiguration.CacheManagerNames.EHCACHE_CACHE_MANAGER)
    @Override
    public String testEhcache(String key) {
        System.out.println("ehcache没有数据,到数据库查询");
        return "testEhcache";
    }

}
