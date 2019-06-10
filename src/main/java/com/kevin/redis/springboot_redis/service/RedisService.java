package com.kevin.redis.springboot_redis.service;

import com.kevin.redis.springboot_redis.entity.User;

/**
 * @Auther: Kevin
 * @Date:
 * @InterfaceName:RedisService
 * @Description: TODO
 */
public interface RedisService {

    void insertUser(User user);
}
