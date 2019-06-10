package com.kevin.redis.springboot_redis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description:    jedis连接池配置
 * @Author:         Kevin
 * @CreateDate:     2019/6/11 2:34
 * @UpdateUser:     Kevin
 * @UpdateDate:     2019/6/11 2:34
 * @UpdateRemark:   修改内容
 * @Version: 1.0
 */
@Configuration
@Slf4j
public class JedisConfig {

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private int maxWaitMillis;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.jedis.pool.max-total}")
    private int maxTotal;

    @Value("${spring.redis.jedis.pool.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.redis.jedis.pool.numTestsPerEvictionRun}")
    private int numTestsPerEvictionRun;

    @Value("${spring.redis.timeout}")
    private int timeout;

    /**
     * 连接池配置
     * @return
     */
    @Bean
    public JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setLifo(true);

        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

        jedisPoolConfig.setBlockWhenExhausted(false);
        jedisPoolConfig.setJmxEnabled(true);

        // 检查连接是否可用
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        log.info("jedisPool配置完成");
        return jedisPoolConfig;
    }


    /**
     * 连接工厂
     * @return
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPassword(password);
        redisStandaloneConfiguration.setPort(port);
        // 获得默认的连接池构造器
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
        // 指定jedisPoolConifig来修改默认的连接池构造器
        jpcb.poolConfig(getJedisPoolConfig());
        // 通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        // 单机配置 + 客户端配置 = jedis连接工厂
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        log.info("jedis连接工厂加载");
        return jedisConnectionFactory;
    }

}
