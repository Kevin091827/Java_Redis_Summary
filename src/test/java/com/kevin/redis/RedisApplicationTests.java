package com.kevin.redis;

import com.kevin.redis.springboot_redis.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {

        contextLoad();
    }


    public void contextLoad() {

        User user = new User();
        user.setAge(12);
        user.setName("kevin");
        redisTemplate.opsForValue().set("user6",user.toString());
        System.out.println(redisTemplate.opsForValue().get("user5"));
    }

}
