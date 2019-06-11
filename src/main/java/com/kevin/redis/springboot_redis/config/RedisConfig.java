package com.kevin.redis.springboot_redis.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;

/**
 * @Description: redis配置类
 * @Author: Kevin
 * @CreateDate: 2019/6/9 16:58
 * @UpdateUser: Kevin
 * @UpdateDate: 2019/6/9 16:58
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
@Configuration
@Slf4j
public class RedisConfig {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * redisTemplate配置
     *
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
        FastJsonRedisSerializer redisSerializer = new FastJsonRedisSerializer(Object.class);
        //配置默认序列化
        redisTemplate.setDefaultSerializer(redisSerializer);
        redisTemplate.setEnableDefaultSerializer(true);
        //配置序列化策略
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
