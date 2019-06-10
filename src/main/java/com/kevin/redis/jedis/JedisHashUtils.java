package com.kevin.redis.jedis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: Jedis hash类型工具类
 * @Author: Kevin
 * @CreateDate: 2019/5/28 13:56
 * @UpdateUser: Kevin
 * @UpdateDate: 2019/5/28 13:56
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class JedisHashUtils {

//    private static Jedis jedis = JedisUtils.getJedis();
//
//    /**
//     * 单一key设值
//     * @param key
//     * @param field
//     * @param value
//     */
//    public static void set(String key,String field,String value){
//        CheckUtils.keyCheck(key);
//        CheckUtils.fieldCheck(field);
//        jedis.hset(key, field, value);
//    }
//
//    /**
//     * 多key设值
//     * @param key
//     * @param map
//     */
//    public static void set(String key, Map<String,String> map){
//        CheckUtils.keyCheck(key);
//        jedis.hset(key, map);
//    }
//
//    /**
//     * 单一value取值
//     * @param key
//     * @param field
//     * @return
//     */
//    public static String get(String key,String field){
//        CheckUtils.keyCheck(key);
//        CheckUtils.fieldCheck(field);
//        return jedis.hget(key, field);
//    }
//
//    /**
//     * 获取key中所有字段和值
//     * @param key
//     * @return
//     */
//    public static Map<String,String> getAll(String key){
//        CheckUtils.keyCheck(key);
//        return jedis.hgetAll(key);
//    }
//
//    /**
//     * 判断是否存在指定的key和field
//     * @param key
//     * @param field
//     * @return
//     */
//    public static boolean isExists(String key,String field){
//        CheckUtils.keyCheck(key);
//        CheckUtils.fieldCheck(field);
//        return jedis.hexists(key, field);
//    }
//
//    /**
//     * 获取key对应的field对应的值得长度
//     * @param key
//     * @param field
//     * @return
//     */
//    public static long strlen(String key,String field){
//        CheckUtils.keyCheck(key);
//        CheckUtils.fieldCheck(field);
//        return jedis.hstrlen(key, field);
//    }
//
//    /**
//     * 获取哈希表中的指定key的所有字段
//     * @param key
//     * @return
//     */
//    public static Set<String> getKeys(String key){
//        CheckUtils.keyCheck(key);
//        return jedis.hkeys(key);
//    }
//
//    /**
//     * 获取哈希表中的指定key的所有值
//     * @param key
//     * @return
//     */
//    public static List<String> getValues(String key){
//        CheckUtils.keyCheck(key);
//        return jedis.hvals(key);
//    }
//
//
//    /**
//     * 删除指定key中的field对应的value
//     * @param key
//     * @param field
//     */
//    public static void del(String key,String...field){
//        CheckUtils.keyCheck(key);
//        jedis.hdel(key,field);
//    }
}
