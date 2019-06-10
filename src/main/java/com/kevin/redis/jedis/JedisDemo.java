package com.kevin.redis.jedis;

import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import javax.swing.*;

/**
 * @Description:    Jedis简单使用demo
 * @Author:         Kevin
 * @CreateDate:     2019/5/27 10:42
 * @UpdateUser:     Kevin
 * @UpdateDate:     2019/5/27 10:42
 * @UpdateRemark:   修改内容
 * @Version: 1.0
 */
public class JedisDemo {

    /**
     * 连接Jedis --- 单实例连接
     * @param ip
     * @param port
     */
    public Jedis connectToJedis(String ip,int port){
        Jedis jedis = new Jedis(ip,port);
        System.out.println("Jedis连接成功！");
        return jedis;
    }

    /**
     * 配置jedis连接池
     * @return
     */
   // @Bean
    public JedisPoolConfig getJedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //连接池中最大连接数
        jedisPoolConfig.setMaxTotal(10);
        //连接池中最大空闲连接数
        jedisPoolConfig.setMaxIdle(5);
        return jedisPoolConfig;
    }

    /**
     * 基于连接池获取jedis连接
     * @param ip
     * @param port
     * @return
     */
    public Jedis getJedisFromJedisPool(String ip,int port){
        JedisPool jedisPool = new JedisPool(getJedisPoolConfig(),ip,port);
        Jedis jedis = jedisPool.getResource();
        System.out.println("获取Jedis连接");
        return jedis;
    }

    public static void main(String[] args){
       JedisStringUtils.set("key1","value1");
       System.out.println(JedisStringUtils.getRange("key1",0,2));
    }


}
