package com.kevin.redis.springboot_redis.service;

import com.kevin.redis.springboot_redis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:RedisServiceImpl
 * @Description: TODO
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void insertUser(User user) {
        try {
            redisTemplate.opsForValue().set("uaadwadawdawdw", user);
        }catch (Exception e){
            System.out.println("redis出错");
        }
    }
}
