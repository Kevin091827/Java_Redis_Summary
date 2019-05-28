## 一，项目简介

本项目的主要目的就是复习一下redis在java中的使用

**主要内容：**

* jedis操作redis（完善）

* springboot集成redis -- redisTemplate使用

* redis持久化 AOF和RDB

* redis事务

* redis消息发布订阅


## 二，分层简介

### jedis操作redis

jedis是redis官方推荐的面向java程序的客户端,封装了很多redis命令,所以我们有必要总结下jedis在java中的使用

以下是我自己封装的工具类
```yaml
com.kevin.redis
    .jedis
      .CheckUtils : 检查工具类
      .JedisKeyUtils : key相关工具类
      .JedisStringUtils : String工具类
      .JedisHashUtils : hash工具类
      .JedisListUtils ：list工具类
      .JedisSetUtils ： set工具类
      .JedisZsetUtils ；zset工具类
      .JedisUtils : jedis连接配置工具
```