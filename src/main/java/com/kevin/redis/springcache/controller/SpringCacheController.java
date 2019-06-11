package com.kevin.redis.springcache.controller;

import com.kevin.redis.springboot_redis.entity.User;
import com.kevin.redis.springcache.service.SpringCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:    DOTO
 * @Author:         Kevin
 * @CreateDate:     2019/6/11 22:36
 * @UpdateUser:     Kevin
 * @UpdateDate:     2019/6/11 22:36
 * @UpdateRemark:   修改内容
 * @Version: 1.0
 */
@Controller
@Slf4j
public class SpringCacheController {

    @Autowired
    private SpringCacheService springCacheService;

    @RequestMapping("/insert")
    @ResponseBody
    public void insertUser() {
        springCacheService.insertUser(12345);
    }

    @RequestMapping("/update")
    @ResponseBody
    public void update() {
        User user = new User();
        user.setAge(12456);
        user.setName("kevin123");
        springCacheService.updateUser(user);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public void delete() {
        springCacheService.deleteUser(12345);
    }

    @RequestMapping("/multil")
    @ResponseBody
    public void multil() {
        springCacheService.selectUser(123);
    }
}
