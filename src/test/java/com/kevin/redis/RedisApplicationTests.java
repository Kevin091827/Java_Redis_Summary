package com.kevin.redis;

import com.kevin.redis.springboot_redis.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableCaching
public class RedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {


        contextLoad();
        System.out.println("缓存完成");
    }


    @Cacheable(key = "#result.name", cacheNames = "user")
    public User contextLoad() {

        User user = new User();
        user.setAge(12);
        user.setName("kevin123");
        System.out.println("adwada");
        return user;
    }

}
