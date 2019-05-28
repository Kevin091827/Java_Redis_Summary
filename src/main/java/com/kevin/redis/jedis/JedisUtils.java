package com.kevin.redis.jedis;

import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

/**
 * @Description:    Jedis工具类
 * @Author:         Kevin
 * @CreateDate:     2019/5/27 11:25
 * @UpdateUser:     Kevin
 * @UpdateDate:     2019/5/27 11:25
 * @UpdateRemark:   修改内容
 * @Version: 1.0
 */
public class JedisUtils {

    private static JedisPool jedisPool;

    /**
     * 初始化jedis连接池
     * @return
     */
    public static void initJedisPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //连接池中最大连接数
        jedisPoolConfig.setMaxTotal(10);
        //连接池中最大空闲连接数
        jedisPoolConfig.setMaxIdle(5);
        jedisPool = new JedisPool(jedisPoolConfig,"127.0.0.1",6379,5000,"123456");
    }

    /**
     * 获取Jedis连接
     * @return
     */
    public static Jedis getJedis(){
        initJedisPool();
        System.out.println("connect to jedis ....");
        return jedisPool.getResource();
    }

    /**
     * 关闭jedis连接
     * @param jedis
     */
    public static void close(Jedis jedis){
        if(jedis != null){
            jedis.close();
        }
    }

    /**
     * 获取所有key
     * @return
     */
    public static Set<String> getAllKeys(){
        return getJedis().keys("*");
    }


}
