package com.kevin.redis.jedis;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @Description:    Jedis list类型工具类
 * @Author:         Kevin
 * @CreateDate:     2019/5/28 13:12
 * @UpdateUser:     Kevin
 * @UpdateDate:     2019/5/28 13:12
 * @UpdateRemark:   修改内容
 * @Version: 1.0
 */
public class JedisListUtils {

    private static Jedis jedis = JedisUtils.getJedis();

    /**
     * 头部插入一个或多个值
     * @param key
     * @param value
     */
    public static void leftPush(String key,String...value){
        CheckUtils.keyCheck(key);
        jedis.lpush(key, value);
    }

    /**
     * 头部插入一个或多个值
     * @param key
     * @param value
     */
    public static void rightPush(String key,String...value){
        CheckUtils.keyCheck(key);
        jedis.rpush(key, value);
    }

    /**
     * 头部弹出一个元素
     * @param key
     */
    public static String leftPop(String key){
        CheckUtils.keyCheck(key);
        String value = jedis.lpop(key);
        return value;
    }

    /**
     * 尾部弹出一个元素
     * @param key
     */
    public static String rightPop(String key){
        CheckUtils.keyCheck(key);
        String value = jedis.rpop(key);
        return value;
    }

    /**
     * 指定位置设值
     * @param key
     * @param index
     * @param value
     */
    public static void leftPush(String key, Long index, String value){
        CheckUtils.keyCheck(key);
        jedis.lset(key,index,value);
    }

    /**
     * 获取指定范围的list
     * @param key
     * @param start
     * @param stop
     * @return
     */
    public static List<String> rangeGet(String key, Long start, Long stop){
        return jedis.lrange(key,start,stop);
    }


}
