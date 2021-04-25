
# sakura-boot快速上手

## 环境准备

### 安装docker

```shell
#查看是否安装了docker，没有就安装docker
docker version

#安装yum-utils：
yum install -y yum-utils device-mapper-persistent-data lvm2

#为yum源添加docker仓库位置：
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
 
#安装docker
yum install docker-ce
 
#启动docker
systemctl start docker
```

### 安装mysql8

```shell
#拉取mysql8镜像
docker pull mysql:8.0.21

#运行mysql
docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=admin  -d mysql:8.0.21

#进入运行MySQL的docker容器
docker exec -it mysql /bin/bash

#使用MySQL命令打开客户端
mysql -uroot -proot --default-character-set=utf8

#创建sakura数据库
create database sakura character set utf8

#给root赋予权限
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%'WITH GRANT OPTION;

#刷新MySQL的系统权限相关表
flush  privileges;
```

### Redis安装

```shell
#拉取Redis6.0的docker镜像
docker pull redis:6

#启动Redis服务
docker run -p 6379:6379 --name redis -d redis:6 redis-server --appendonly yes
```



### RabbitMQ安装

```shell
#拉取rabbitmq3.7.15的docker镜像
docker pull rabbitmq:3.7.15

#启动RabbitMQ服务
docker run -p 5672:5672 -p 15672:15672 --name rabbitmq -d rabbitmq:3.7.15

#进入容器并开启管理功能
docker exec -it rabbitmq /bin/bash
rabbitmq-plugins enable rabbitmq_management
```

+ 访问地址查看是否安装成功：http://ip:port，默认账号密码都为：guest

  ![image-20210425194536659](https://github.com/yanjingfan/sakura-boot-demo/blob/master/docs/pic/image-20210425194536659.png)

+ 登陆后创建`sakura`帐号，密码也为`sakura`，并设置其角色为管理员

  ![image-20210425195241162](https://github.com/yanjingfan/sakura-boot-demo/blob/master/docs/pic/image-20210425195241162.png)

+ 创建一个新的虚拟host为：/sakura

  ![image-20210425195553704](https://github.com/yanjingfan/sakura-boot-demo/blob/master/docs/pic/image-20210425195553704.png)

+ 点击`sakura`用户进入用户配置页面

  ![image-20210425195801851](https://github.com/yanjingfan/sakura-boot-demo/blob/master/docs/pic/image-20210425195801851.png)

+ 进入`sakura`用户配置页面后，为其设置`/sakura`虚拟host的权限即可

  ![image-20210425200157143](https://github.com/yanjingfan/sakura-boot-demo/blob/master/docs/pic/image-20210425200157143.png)



### fastdfs安装

+ 拉取镜像

  ```shell
  docker pull qbanxiaoli/fastdfs
  ```

+ 启动fastdfs

  ```shell
  docker run -d --restart=always --privileged=true --net=host --name=fastdfs -e IP=192.168.1.130 qbanxiaoli/fastdfs
  ```

+ 测试fastdfs是否搭建成功

  ```shell
  docker exec -it fastdfs /bin/bash
  
  echo "Hello FastDFS!">index.html
  
  fdfs_test /etc/fdfs/client.conf upload index.html
  ```

  ![image-20210425215816914](https://github.com/yanjingfan/sakura-boot-demo/blob/master/docs/pic/image-20210425215816914.png)
  
+ 访问返回来的url，可以访问就表示搭建成功

  ![image-20210425220038541](https://github.com/yanjingfan/sakura-boot-demo/blob/master/docs/pic/image-20210425220038541.png)

## 启动项目

运行`DemoApplication`中`main`方法，访问`localhost:8080/doc.html`测试接口

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

   代码示例：[AppsServiceFeignClient](https://github.com/yanjingfan/sakura-boot-demo/blob/master/src/main/java/com/sakura/cloud/demo1/remote/AppsServiceFeignClient.java)

3. 添加`feign层`

   调用远程服务接口处理

   代码示例：[AppsFeignClientFallbackFactory](https://github.com/yanjingfan/sakura-boot-demo/blob/master/src/main/java/com/sakura/cloud/demo1/remote/feign/AppsFeignClientFallbackFactory.java)

4. 远程接口调用示例

   参考[RemoteApiController](https://github.com/yanjingfan/sakura-boot-demo/blob/master/src/main/java/com/sakura/cloud/demo1/controller/RemoteApiController.java)类中的queryApps方法，在service层注入`AppsServiceFeignClient`对象，即可调用远程接口



## 使用restTemplate调用远程接口

RestTemplate 提供了多种便捷访问远程Http服务的方法，能够大大提高客户端的编写效率。

可参考demo工程中的[RemoteApiController](https://github.com/yanjingfan/sakura-boot-demo/blob/master/src/main/java/com/sakura/cloud/demo1/controller/RemoteApiController.java)类中的`queryRemoteUsers`和`queryRemoteApps`方法，分别提供了`GET`和`POST`两种远程请求方式的示例，编码简单高效，也不再需要手动关闭资源。





















