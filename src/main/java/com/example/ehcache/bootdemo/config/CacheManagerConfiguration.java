package com.example.ehcache.bootdemo.config;

import com.google.common.collect.ImmutableMap;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.Map;

/**
 * @author QiuPengJun
 * @version 1.0
 * @date 2020/7/29 15:57
 */
@Configuration
@EnableCaching
@EnableConfigurationProperties(CacheProperties.class)
public class CacheManagerConfiguration {
    private final CacheProperties cacheProperties;
    public CacheManagerConfiguration(CacheProperties cacheProperties)    {
        this.cacheProperties = cacheProperties;
    }
    public interface CacheManagerNames {
        String REDIS_CACHE_MANAGER = "redisCacheManager";
        String EHCACHE_CACHE_MANAGER = "ehCacheManager";
    }

    @Bean(name = CacheManagerNames.REDIS_CACHE_MANAGER)
    public RedisCacheManager redisCacheManager(RedisConnectionFactory factory) {
        Map<String, RedisCacheConfiguration> expires = ImmutableMap.<String, RedisCacheConfiguration>builder()
                .put("15sCache", RedisCacheConfiguration.defaultCacheConfig().entryTtl(
                        Duration.ofMillis(15000)
                ))
                .put("30sCache", RedisCacheConfiguration.defaultCacheConfig().entryTtl(
                        Duration.ofMillis(30000)
                ))
                .put("60sCache", RedisCacheConfiguration.defaultCacheConfig().entryTtl(
                        Duration.ofMillis(60000)
                ))
                .put("120sCache", RedisCacheConfiguration.defaultCacheConfig().entryTtl(
                        Duration.ofMillis(120000)
                ))
                .build();


        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(factory)
                .withInitialCacheConfigurations(expires)
                .build();
    }

    @Bean(name = CacheManagerNames.EHCACHE_CACHE_MANAGER)
    @Primary
    public EhCacheCacheManager ehCacheManager() {
        Resource resource = this.cacheProperties.getEhcache().getConfig();
        resource = this.cacheProperties.resolveConfigLocation(resource);
        EhCacheCacheManager ehCacheManager = new EhCacheCacheManager(
                EhCacheManagerUtils.buildCacheManager(resource)
        );
        ehCacheManager.afterPropertiesSet();
        return ehCacheManager;
    }
}
