package com.visitscotland.brxm.config;



import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CachingConfig  {

   /* @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("dmsProduct","externalDocuments");
    }*/

    @Bean
    @Primary
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("dmsProduct");
        cacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(3600, TimeUnit.SECONDS)
                .initialCapacity(2000)
                .maximumSize(5000)
                .recordStats());
        return cacheManager;
    }

    @Bean
    public CacheManager alternateCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("externalDocuments");
        cacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.SECONDS)
                .initialCapacity(2000)
                .maximumSize(5000)
                .recordStats());
        return cacheManager;
    }

}
