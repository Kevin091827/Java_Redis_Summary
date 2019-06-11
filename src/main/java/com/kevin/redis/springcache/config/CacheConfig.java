package com.kevin.redis.springcache.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.redis.springboot_redis.config.JedisConfig;
import com.kevin.redis.springboot_redis.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @Description:    springboot2.x整合spring cache配置类
 * @Author:         Kevin
 * @CreateDate:     2019/6/11 21:56
 * @UpdateUser:     Kevin
 * @UpdateDate:     2019/6/11 21:56
 * @UpdateRemark:   修改内容
 * @Version: 1.0
 */
@Configuration
@Slf4j
public class CacheConfig extends CachingConfigurerSupport {

    /**
     * 自定义缓存管理器
     * @param factory   自动注入spring容器中的jedisConnectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(JedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);

        // 配置序列化（解决乱码的问题）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 7 天缓存过期
                .entryTtl(Duration.ofDays(7))
                // key序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                // 值序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer))
                // 不缓存空值
                .disableCachingNullValues();
        // 通过连接工厂构建缓存管理器
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                // 注入配置
                .cacheDefaults(config)
                .build();
        return cacheManager;
    }


    @Override
    public CacheResolver cacheResolver() {
        return super.cacheResolver();
    }

    /**
     * 自定义key生成器
     * key ---> hashCode( 类名（class）+ 方法名（method)+ 所有参数（params）)
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            // 采取拼接的方式生成key
            StringBuilder stringBuilder = new StringBuilder();
            // 目标类的类名
            stringBuilder.append(target.getClass().getName());
            stringBuilder.append(":");
            // 目标方法名
            stringBuilder.append(method.getName());
            // 参数
            for (Object object : params) {
                stringBuilder.append(":" + String.valueOf(object));
            }
            String result = String.valueOf(stringBuilder.hashCode());
            log.info("自定义的key为：" + result);
            return result;
        };
    }


    /**
     * 处理缓存读写异常（缓存自定义异常）
     * @return
     */
    @Override
    @Bean
    public CacheErrorHandler errorHandler() {

        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {

            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                log.error("缓存 查找 失败，失败原因：" + e.getMessage() + "缓存key：" + key);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object o1) {
                log.error("缓存 更新 失败，失败原因：" + e.getMessage() + "缓存key：" + key);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                log.error("缓存 删除 失败，失败原因：" + e.getMessage() + "缓存key：" + key);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                log.error("缓存 清除 失败，失败原因：" + e.getMessage());
            }
        };
        return cacheErrorHandler;
    }
}
