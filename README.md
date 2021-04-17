
# sakura-boot快速上手

## 环境准备

推荐使用docker安装，快速体验

+ mysql8.0
+ rabbitmq
+ redis6
+ fastdfs

新建一个叫`sakura`的库，然后使用`docs/sql/V1__Init.sql`脚本建表

启动项目

访问`localhost:8080/doc.html`测试接口

## 目录结构说明

```txt
sakura-demo/
├── docs	# 项目文件
├── pom.xml	
├── README.md # 文档
└── src
    ├── main
    │   ├── docker
    │   │   └── Dockerfile	# 构建镜像文件
    │   ├── java
    │   │   └── com
    │   │       └── sakura
    │   │           └── cloud
    │   │               ├── demo1			 # 和demo2的业务不一样，得分开
    │   │               │   ├── controller 	# 前端交互层
    │   │               │   ├── mapper		# 数据库交互层
    │   │               │   ├── po 			# 数据库映射对象
    │   │               │   ├── remote		# 远程调用接口
    │   │               │   │   └── feign  # 远程调用接口实现类
    │   │               │   ├── service		# 业务处理接口层
    │   │               │   │   └── impl   # 业务处理具体实现层
    │   │               │   └── vo		    # 前端显示对象 
    │   │               ├── demo2
    │   │               └── DemoApplication.java
    │   └── resources
    │       ├── application.yml
    │       ├── bootstrap.yml
    │       └── mapper
    │           ├── demo1
    │           │   └── userMapper.xml
    │           └── demo2
    └── test
        └── java
            └── com
                └── sakura
                    └── cloud
                        ├── Base64AndMD5Test.java  # Base64和MD5加密解密示例
                        └── URLCodecTest.java	   # URL转码解码示例
```

## 动态修改输出日志等级

默认不开启此功能，如需开启，添加如下配置

```yaml
log:
  update-level:
    enable: true
```

### 接口详情

**修改输出日志等级接口:** `/log/{level}/{pkn}`

**请求类型：**`POST`请求

**请求参数：**

| 参数名 | 是否必填 | 类型   | 说明                                                        |
| ------ | -------- | ------ | ----------------------------------------------------------- |
| level  | 是       | string | 日志等级参数（TRACE, DEBUG, INFO, WARN, ERROR, FATAL, OFF） |
| pkn    | 是       | String | 需要改变日志等级的包                                        |



### 示例

需求：项目中的`com.sakura.cloud.demo1.controller`包中只打印`ERROR`日志

操作：使用`postman`等一些工具发送`post`请求

请求路径和参数：`localhost:8080/log/ERROR/com.sakura.cloud.demo1.controller`

返回结果：

```json
{
    "code": 200,
    "message": "操作成功",
    "data": "[com.ly.cloud.demo1.controller]包下的日志级别成功改为[ERROR]",
}
```

测试验证：

访问`LogChangeController`中的`/test`接口，控制台只打印error日志

```txt
2021-01-15 15:26:48.691 ERROR 11248 --- [nio-8080-exec-4] c.s.c.d.controller.LogChangeController   : 这是一个error日志...
```



## 跨服务调用

> 需要在启动类上添加`@EnableFeignClients`注解

1. 需要在启动类上添加`@EnableFeignClients`注解

   ```java
   @EnableFeignClients
   public class DemoApplication {
       public static void main(String[] args) {
           SpringApplication.run(DemoApplication.class, args);
       }
   }
   ```

2. 添加`remote层`

   代码示例：[AppsServiceFeignClient]()

3. 添加`feign层`

   调用远程服务接口处理

   代码示例：[AppsFeignClientFallbackFactory]()

4. 远程接口调用示例

   参考[RemoteApiController]()类中的queryApps方法，在service层注入`AppsServiceFeignClient`对象，即可调用远程接口



## 使用restTemplate调用远程接口

RestTemplate 提供了多种便捷访问远程Http服务的方法，能够大大提高客户端的编写效率。

可参考demo工程中的[RemoteApiController]()类中的`queryRemoteUsers`和`queryRemoteApps`方法，分别提供了`GET`和`POST`两种远程请求方式的示例，编码简单高效，也不再需要手动关闭资源。





















