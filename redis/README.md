### Redis安装

```shell
#拉取Redis6.0的docker镜像
docker pull redis:6

#启动Redis服务
docker run -p 6379:6379 --restart=always --name redis -d redis:6 redis-server --appendonly yes
```

+ 加入依赖

  ```xml
  <dependency>
      <groupId>com.sakura</groupId>
      <artifactId>sakura-cache</artifactId>
      <version>1.0</version>
  </dependency>
  ```

+ yml配置

  ```yaml
  spring
    redis:
      database: 0           # Redis数据库索引（默认为0）
      host: 192.168.1.130     # Redis服务器地址
      port: 6379           # Redis服务器连接端口
      password: 37621040      #Redis服务器连接密码（默认为空）
      timeout: 5000ms            # 连接超时时间（毫秒）
      lettuce:				# 使用默认的redis连接池
        pool:
          max-idle: 8         # 连接池中的最大空闲连接
          min-idle: 1         # 连接池中的最小空闲连接
          max-active: 8       # 连接池最大连接数（使用负值表示没有限制）
          max-wait: -1ms        # 连接池最大阻塞等待时间（使用负值表示没有限制）
  ```
+ redis操作实现类：[RedisUtils](https://github.com/yanjingfan/sakura-boot/blob/master/sakura-cache/src/main/java/com/sakura/common/cache/RedisUtils.java)
+ 使用redis工具类示例代码：[ RedisController](https://github.com/yanjingfan/sakura-boot-demo/blob/master/redis/src/main/java/com/sakura/cloud/controller/RedisController.java)