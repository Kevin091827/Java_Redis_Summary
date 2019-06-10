package com.kevin.redis.jedis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @Description: Jedis key工具类
 * @Author: Kevin
 * @CreateDate: 2019/5/28 14:02
 * @UpdateUser: Kevin
 * @UpdateDate: 2019/5/28 14:02
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class JedisKeyUtils {

    private static Jedis jedis = JedisUtils.getJedis();

    /**
     * 通过key判断值得类型
     *
     * @param key
     * @return
     */
    public static String type(String key) {
        return jedis.type(key);
    }

    /**
     * 返回满足pattern表达式的所有key
     * keys(*)
     * 返回所有的key
     *
     * @param pattern key表达式
     * @return
     */
    public static Set<String> keys(String pattern) {
        return jedis.keys(pattern);
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public static boolean isExist(String key) {
        return jedis.exists(key);
    }
}
