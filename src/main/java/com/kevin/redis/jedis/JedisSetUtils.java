package com.kevin.redis.jedis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @Description:    Jedis Set类型工具类
 * @Author:         Kevin
 * @CreateDate:     2019/5/28 13:12
 * @UpdateUser:     Kevin
 * @UpdateDate:     2019/5/28 13:12
 * @UpdateRemark:   修改内容
 * @Version: 1.0
 */
public class JedisSetUtils {

    private static Jedis jedis = JedisUtils.getJedis();

    /**
     * 通过key向指定的set中添加value
     * @param key
     * @param members 可以是一个String 也可以是一个String数组
     * @return 添加成功的个数
     */
    public static Long sadd(String key, String... members) {
        return jedis.sadd(key, members);
    }

    /**
     * 通过key删除set中对应的value值
     * @param key
     * @param members 可以是一个String 也可以是一个String数组
     * @return 删除的个数
     */
    public static Long srem(String key, String... members) {
        return jedis.srem(key, members);
    }

    /**
     * 通过key随机删除一个set中的value并返回该值
     * @param key
     * @return
     */
    public static String spop(String key) {
        return jedis.spop(key);
    }

    /**
     * 通过key获取set中的差集
     * 以第一个set为标准
     * @param keys 可以 是一个string 则返回set中所有的value 也可以是string数组
     * @return
     */
    public static Set<String> sdiff(String... keys) {
        return jedis.sdiff(keys);
    }

    /**
     * 通过key获取set中的差集并存入到另一个key中
     * 以第一个set为标准
     * @param dstkey 差集存入的key
     * @param keys   可以 是一个string 则返回set中所有的value 也可以是string数组
     * @return
     */
    public static Long sdiffstore(String dstkey, String... keys) {
        return jedis.sdiffstore(dstkey, keys);
    }

    /**
     * 通过key获取指定set中的交集
     * @param keys 可以 是一个string 也可以是一个string数组
     * @return
     */
    public static Set<String> sinter(String... keys) {
        return jedis.sinter(keys);
    }

    /**
     * 通过key获取指定set中的交集 并将结果存入新的set中
     * @param dstkey
     * @param keys   可以 是一个string 也可以是一个string数组
     * @return
     */
    public static Long sinterstore(String dstkey, String... keys) {
        return jedis.sinterstore(dstkey, keys);
    }

    /**
     * 通过key返回所有set的并集
     * @param keys 可以 是一个string 也可以是一个string数组
     * @return
     */
    public static Set<String> sunion(String... keys) {
        return jedis.sunion(keys);
    }

    /**
     * 通过key返回所有set的并集,并存入到新的set中
     * @param dstkey
     * @param keys   可以 是一个string 也可以是一个string数组
     * @return
     */
    public static Long sunionstore(String dstkey, String... keys) {
        return jedis.sunionstore(dstkey, keys);
    }

    /**
     * 通过key将set中的value移除并添加到第二个set中
     *
     * @param srckey 需要移除的
     * @param dstkey 添加的
     * @param member set中的value
     * @return
     */
    public static Long smove(String srckey, String dstkey, String member) {
        return jedis.smove(srckey, dstkey, member);
    }

    /**
     * 通过key获取set中value的个数
     * @param key
     * @return
     */
    public static Long scard(String key) {
        return jedis.scard(key);
    }

    /**
     * 通过key判断value是否是set中的元素
     * @param key
     * @param member
     * @return
     */
    public static Boolean sismember(String key, String member) {
        return jedis.sismember(key, member);
    }

    /**
     * 通过key获取set中随机的value,不删除元素
     * @param key
     * @return
     */
    public static String srandmember(String key) {
        return jedis.srandmember(key);
    }

    /**
     * 通过key获取set中所有的value
     * @param key
     * @return
     */
    public static Set<String> smembers(String key) {
        return jedis.smembers(key);
    }


}
