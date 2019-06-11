package com.kevin.redis.springcache.service;

import com.kevin.redis.springboot_redis.entity.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:SpringCacheServiceImpl
 * @Description: TODO
 */
@Service
public class SpringCacheServiceImpl implements SpringCacheService{

    /**
     * 新增缓存
     * @param age
     * @return
     */
    @Override
    @Cacheable(key = "#age", cacheNames = "user")
    public User insertUser(int age) {

        User user = new User();
        user.setAge(age);
        user.setName("kevin123");
        System.out.println("adwada");
        return user;
    }

    /**
     * 更新缓存
     * @param user
     * @return
     */
    @CachePut(key = "#user.age",cacheNames = "user")
    @Override
    public User updateUser(User user) {

        user.setName("kevin12453");
        System.out.println("adwada");
        return user;
    }

    /**
     * 删除缓存
     * @param age
     * @return
     */
    @CacheEvict(key = "#age",cacheNames = "user")
    @Override
    public int deleteUser(int age) {
        System.out.println("删除成功");
        return 0;
    }


    /**
     * 多种缓存规则
     * @param age
     * @return
     */
    @Caching(cacheable = {
            @Cacheable(key = "#age",cacheNames = "user")
    },put = {
            @CachePut(key = "#result.name",cacheNames = "user")
    })
    @Override
    public User selectUser(int age) {

        User user = new User();
        user.setAge(age);
        user.setName("kevin123233");
        System.out.println("adwadawdawdawa");
        return user;
    }
}
