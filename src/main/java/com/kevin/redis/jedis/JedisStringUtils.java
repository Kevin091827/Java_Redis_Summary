package com.kevin.redis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.List;

/**
 * @Description:    Jedis String类型工具类
 * @Author:         Kevin
 * @CreateDate:     2019/5/28 13:12
 * @UpdateUser:     Kevin
 * @UpdateDate:     2019/5/28 13:12
 * @UpdateRemark:   修改内容
 * @Version: 1.0
 */
public class JedisStringUtils {

    private static Jedis jedis = JedisUtils.getJedis();

    /**
     * 根据key获取String类型值
     * @param key
     * @return
     */
    public static String get(String key){
         CheckUtils.keyCheck(key);
         String value =  jedis.get(key);
         return value;
    }

    /**
     * 为字符串类型设值 --- 不带过期时间
     * @param key
     * @param value
     */
    public static void set(String key,String value){
        CheckUtils.keyCheck(key);
        jedis.set(key, value);
    }

    /**
     * 为字符串类型设值 --- 带过期时间(毫秒)
     *  px：long millisecondsToExpire
     *  ex：int secondsToExpire
     *
     * @param key
     * @param value
     * @param expire
     */
    public static void set(String key,String value,int expire){
        CheckUtils.keyCheck(key);
        jedis.set(key, value,new SetParams().px(expire));
    }

    /**
     * 获取子字符串
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static String getRange(String key,long start,long end){
        String value =  jedis.getrange(key, start, end);
        return value;
    }


    /**
     * 根据key删除值
     * @param key
     */
    public static Long del(String key){
        CheckUtils.keyCheck(key);
        Long i = jedis.del(key);
        return i;
    }

    /**
     * 删除多个key对应的值
     * @param keys
     * @return
     */
    public static Long del(String[] keys){
        Long i =  jedis.del(keys);
        return i;
    }

    /**
     * 更改键为key的值为新值并且获取该key对应的旧值
     * @param key
     * @param newValue
     * @return
     */
    public static String getAndSet(String key,String newValue){
        CheckUtils.keyCheck(key);
        String oldValue =  jedis.getSet(key, newValue);
        return oldValue;
    }

    /**
     * 在指定key对应的value末端拼接参数value
     * @param key
     * @param value
     */
    public static void appandValue(String key,String value){
        CheckUtils.keyCheck(key);
        jedis.append(key, value);
    }

    /**
     * 获取指定key对应value的字符串长度
     * @param key
     * @return
     */
    public static Long strlen(String key){
        CheckUtils.keyCheck(key);
        Long length =  jedis.strlen(key);
        return length;
    }

    /**
     * 获取多个key对应的值
     * @param keys
     * @return
     */
    public static List<String> getValueList(String[] keys){
        List<String> list =  jedis.mget(keys);
        return list;
    }

    /**
     * 通过key 和offset 从指定的位置开始将原先value替换
     * @param key
     * @param offset
     * @param str
     * @return
     */
    public static Long setrange(String key, int offset, String str) {
        return jedis.setrange(key, offset, str);
    }
}
