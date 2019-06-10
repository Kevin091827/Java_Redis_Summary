package com.kevin.redis.jedis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @Description: Jedis Zset类型工具类
 * @Author: Kevin
 * @CreateDate: 2019/5/28 13:12
 * @UpdateUser: Kevin
 * @UpdateDate: 2019/5/28 13:12
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class JedisZsetUtils {

    private static Jedis jedis = JedisUtils.getJedis();

    /**
     * 通过key向zset中添加value,score,其中score就是用来排序的
     * 如果该value已经存在则根据score更新元素
     *
     * @param key
     * @param score
     * @param member
     * @return
     */
    public static Long zadd(String key, double score, String member) {
        return jedis.zadd(key, score, member);
    }

    /**
     * 通过key删除在zset中指定的value
     *
     * @param key
     * @param members 可以 是一个string 也可以是一个string数组
     * @return
     */
    public static Long zrem(String key, String... members) {
        return jedis.zrem(key, members);
    }

    /**
     * 通过key增加该zset中value的score的值
     *
     * @param key
     * @param score
     * @param member
     * @return
     */
    public static Double zincrby(String key, double score, String member) {
        return jedis.zincrby(key, score, member);
    }

    /**
     * 通过key返回zset中value的排名
     * 下标从小到大排序
     *
     * @param key
     * @param member
     * @return
     */
    public static Long zrank(String key, String member) {
        return jedis.zrank(key, member);
    }

    /**
     * 通过key返回zset中value的排名
     * 下标从大到小排序
     *
     * @param key
     * @param member
     * @return
     */
    public static Long zrevrank(String key, String member) {
        return jedis.zrevrank(key, member);
    }

    /**
     * 通过key将获取score从start到end中zset的value
     * socre从大到小排序
     * 当start为0 end为-1时返回全部
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<String> zrevrange(String key, long start, long end) {
        return jedis.zrevrange(key, start, end);
    }

    /**
     * 通过key返回指定score内zset中的value
     *
     * @param key
     * @param max
     * @param min
     * @return
     */
    public static Set<String> zrangebyscore(String key, String max, String min) {
        return jedis.zrevrangeByScore(key, max, min);
    }

    /**
     * 通过key返回指定score内zset中的value
     *
     * @param key
     * @param max
     * @param min
     * @return
     */
    public static Set<String> zrangeByScore(String key, double max, double min) {
        return jedis.zrevrangeByScore(key, max, min);
    }

    /**
     * 返回指定区间内zset中value的数量
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Long zcount(String key, String min, String max) {
        return jedis.zcount(key, min, max);
    }

    /**
     * 通过key返回zset中的value个数
     *
     * @param key
     * @return
     */
    public static Long zcard(String key) {
        return jedis.zcard(key);
    }

    /**
     * 通过key获取zset中value的score值
     *
     * @param key
     * @param member
     * @return
     */
    public static Double zscore(String key, String member) {
        return jedis.zscore(key, member);
    }

    /**
     * 通过key删除给定区间内的元素
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Long zremrangeByRank(String key, long start, long end) {
        return jedis.zremrangeByRank(key, start, end);
    }

    /**
     * 通过key删除指定score内的元素
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Long zremrangeByScore(String key, double start, double end) {
        return jedis.zremrangeByScore(key, start, end);
    }


}
