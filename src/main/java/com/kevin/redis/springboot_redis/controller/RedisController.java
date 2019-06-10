package com.kevin.redis.springboot_redis.controller;

import com.kevin.redis.springboot_redis.entity.User;
import com.kevin.redis.springboot_redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: Kevin
 * @Date:
 * @ClassName:RedisController
 * @Description: TODO
 */
@Controller
public class RedisController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/insertUser")
    @ResponseBody
    public void insertUser() {

        User user = new User();
        user.setAge(1555456);
        user.setName("kevin");
        for (int i = 0; i < 8; i++) {
            redisService.insertUser(user);
        }
    }
}

