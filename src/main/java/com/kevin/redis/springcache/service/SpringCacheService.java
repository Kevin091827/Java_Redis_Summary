package com.kevin.redis.springcache.service;

import com.kevin.redis.springboot_redis.entity.User;

/**
 * @Auther: Kevin
 * @Date:
 * @InterfaceName:SpringCacheService
 * @Description: TODO
 */
public interface SpringCacheService {

    User insertUser(int age);

    User selectUser(int age);

    User updateUser(User user);

    int deleteUser(int age);
}
