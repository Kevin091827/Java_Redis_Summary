package com.kevin.redis.jedis;

/**
 * @Description: Jedis 工具类检查工具
 * @Author: Kevin
 * @CreateDate: 2019/5/28 13:14
 * @UpdateUser: Kevin
 * @UpdateDate: 2019/5/28 13:14
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class CheckUtils {

    /**
     * 检查key设置问题 --- 是否为空值或者null
     *
     * @param key
     */
    public static void keyCheck(String key) {
        if (" ".equals(key) || key == null) {
            try {
                throw new Exception("key出错");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检查field设置问题 --- 是否为空值或者null
     *
     * @param field
     */
    public static void fieldCheck(String field) {
        if (" ".equals(field) || field == null) {
            try {
                throw new Exception("field出错");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
