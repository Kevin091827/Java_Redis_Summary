# 一，spring cache

## 简介

注解驱动的缓存

# 二，springboot2.0整合spring cache + redis手动配置

## 1.新建redis配置类RedisConfig

### 继承 CachingConfigurerSupport类完成对redis的基本配置
```java
public class CachingConfigurerSupport implements CachingConfigurer {
    public CachingConfigurerSupport() {
    }

    //自定义缓存管理器
    @Nullable
    public CacheManager cacheManager() {
        return null;
    }

    //通过自定义CacheResolver实现动态选择CacheManager
    @Nullable
    public CacheResolver cacheResolver() {
        return null;
    }

    //自定义key生成策略
    @Nullable
    public KeyGenerator keyGenerator() {
        return null;
    }

    //自定义缓存读写异常
    @Nullable
    public CacheErrorHandler errorHandler() {
        return null;
    }
}
```

#### CacheErrorHandler

CacheErrorHandler是一个缓存异常处理接口，定义了缓存读写异常的方法
```java
public interface CacheErrorHandler {
    void handleCacheGetError(RuntimeException var1, Cache var2, Object var3);

    void handleCachePutError(RuntimeException var1, Cache var2, Object var3, @Nullable Object var4);

    void handleCacheEvictError(RuntimeException var1, Cache var2, Object var3);

    void handleCacheClearError(RuntimeException var1, Cache var2);
}
```
缓存仅仅是为了业务更快地查询而存在的，如果因为缓存操作失败导致正常的业务流程失败，有点得不偿失了。因此需要开发者自定义CacheErrorHandler处理缓存读写的异常。

redis缓存读写异常的默认实现是SimpleCacheErrorHandler

通过查看源码可以知道，SimpleCacheErrorHandler对每种错误都是简单的抛出一个Exception
```java
public class SimpleCacheErrorHandler implements CacheErrorHandler {
    public SimpleCacheErrorHandler() {
    }

    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        throw exception;
    }

    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, @Nullable Object value) {
        throw exception;
    }

    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        throw exception;
    }

    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        throw exception;
    }
}
```

言归正传，自定义缓存异常只需要重写这四个方法即可
```java
    /**
     * 处理缓存读写异常（缓存自定义异常）
     * @return
     */
    @Override
    public CacheErrorHandler errorHandler() {

       CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {

           @Override
           public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                log.error("缓存 查找 失败，失败原因："+e.getMessage()+"缓存key："+key);
           }

           @Override
           public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object o1) {
               log.error("缓存 更新 失败，失败原因："+e.getMessage()+"缓存key："+key);
           }

           @Override
           public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
               log.error("缓存 删除 失败，失败原因："+e.getMessage()+"缓存key："+key);
           }

           @Override
           public void handleCacheClearError(RuntimeException e, Cache cache) {
               log.error("缓存 清除 失败，失败原因："+e.getMessage());
           }
       };
       return cacheErrorHandler;
    }
```

#### KeyGenerator

缓存key生成器，定义了缓存key的生成方法
```java
@FunctionalInterface
public interface KeyGenerator {
    Object generate(Object var1, Method var2, Object... var3);
}
```
spring cache缓存的默认key生成策略是SimpleKeyGenerator

```java
public class SimpleKeyGenerator implements KeyGenerator {
    public SimpleKeyGenerator() {
    }

    public Object generate(Object target, Method method, Object... params) {
        return generateKey(params);
    }

    //生成key
    public static Object generateKey(Object... params) {
        //没有方法参数：key = 0
        if (params.length == 0) {
            return SimpleKey.EMPTY;
        } else {
            //参数个数为1：key = 第一个参数
            if (params.length == 1) {
                Object param = params[0];
                if (param != null && !param.getClass().isArray()) {
                    return param;
                }
            }
            //如果参数多于一个的话则使用所有参数的hashCode作为key。
            return new SimpleKey(params);
        }
    }
}
```
场景：当我们先调用了getModel1(1)，ehcache就会将方法的返回结果以"1"为key放入缓存中，当我们再调用getModel2(1)时，ehcache就会从缓存中找key为"1"的数据(即 Model1 )并试图将它转换为Model2 ，这就出现了异常:  Model1 can not be cast to Model2.....所以我们需要自定义key策略来解决这个问题，将类名和方法名和参数列表一起来生成key，下面是自定义的Key生成代码：
```java
    /**
     * 自定义key生成器
     *
     * key ---> hashCode( 类名（class）+ 方法名（method)+ 所有参数（params）)
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target,method,params)->{
            // 采取拼接的方式生成key
            StringBuilder stringBuilder = new StringBuilder();
            // 目标类的类名
            stringBuilder.append(target.getClass().getName());
            stringBuilder.append(":");
            // 目标方法名
            stringBuilder.append(method.getName());
            // 参数
            for(Object object : params){
                stringBuilder.append(":"+String.valueOf(object));
            }
            String result = String.valueOf(stringBuilder.hashCode());
            log.info("自定义的key为："+result);
            return result;
        };
    }
```

#### cacheManager
如果需要Spring缓存可以正常工作，必须配置一个CacheManager。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190611221158541.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MTkyMjI4OQ==,size_16,color_FFFFFF,t_70)
CacheManager简单描述就是用来存放Cache，Cache用于存放具体的key-value值

在我们没有配置cacheConfiguration时，默认使用的是SimpleCacheConfiguration，其缓存管理器是ConcurrentMapCacheManager

```java
 @Bean
    public ConcurrentMapCacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        List<String> cacheNames = this.cacheProperties.getCacheNames();
        if (!cacheNames.isEmpty()) {
            cacheManager.setCacheNames(cacheNames);
        }

        return (ConcurrentMapCacheManager)this.customizerInvoker.customize(cacheManager);
    }
```
ConcurrentMapCacheManager通过一个ConcurrentMap作为缓存容器存放缓存
```java
ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap(16);
```
查询缓存cache时根据cacheName去取缓存
```java
    @Nullable
    public Cache getCache(String name) {
        Cache cache = (Cache)this.cacheMap.get(name);
        if (cache == null && this.dynamic) {
            ConcurrentMap var3 = this.cacheMap;
            synchronized(this.cacheMap) {
                cache = (Cache)this.cacheMap.get(name);
                if (cache == null) {
                    cache = this.createConcurrentMapCache(name);
                    this.cacheMap.put(name, cache);
                }
            }
        }

        return cache;
    }
```

所以我们定义一个RedisCacheManager目的就是为了操作redisCache（cache接口实现类）

```java
    /**
     * 自定义缓存管理器
     * @param factory   自动注入spring容器中的jedisConnectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(JedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);

        // 配置序列化（解决乱码的问题）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 7 天缓存过期
                .entryTtl(Duration.ofDays(7))
                // key序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                // 值序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer))
                // 不缓存空值
                .disableCachingNullValues();
        // 通过连接工厂构建缓存管理器
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                // 注入配置
                .cacheDefaults(config)
                .build();
        return cacheManager;
    }
```

## 2.使用基于注解的缓存

缓存注解功能不止如此，需要时网上查阅

```java
@Service
public class SpringCacheServiceImpl implements SpringCacheService{

    /**
     * 新增缓存
     * @param age
     * @return
     */
    @Override
    @Cacheable(key = "#result.name", cacheNames = "user")
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
    @CacheEvict(key = "#p0",cacheNames = "user")
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
```


缓存结果：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190611225927416.png)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190611230117536.png)

## 3.注意问题

###  @Cacheable注解不生效问题

 @Cacheable注解中：一个方法A调同一个类里的另一个有缓存注解的方法B，这样是不走缓存的。例如在同一个service里面两个方法的调用，缓存是不生效的

**为什么缓存没有被正常创建？？**

因为@Cacheable 是使用AOP 代理实现的 ，通过创建内部类来代理缓存方法，这样就会导致一个问题，类内部的方法调用类内部的缓存方法不会走代理，不会走代理，就不能正常创建缓存，所以每次都需要去调用数据库。