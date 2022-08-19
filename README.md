## 各demo模块介绍

**注意**：各`demo`都使用`nacos`发现、配置和管理微服务，如不需要，去掉`pom.xml`中的`sakura-ms`依赖即可

| 模块名           | 模块介绍                           | 启动端口               | 依赖的中间件         |
|:-------------:|:------------------------------:|:------------------:|:--------------:|
| dynamic-cron  | 动态定时任务示例                       | 8087               | mysql          |
| es            | elasticsearch通用查询示例            | 8095               | es             |
| fastdfs       | fastdfs工具类文件上传下载示例             | 8081               | fastdfs        |
| gateway       | SpringCloud Gateway网关          | 8888               | redis          |
| jpa           | jpa+querydsl常见的crud示例          | 8082               | mysql          |
| minio         | minio工具类文件上传示例                 | 8083               | mino           |
| oauth2        | 认证服务，负责对登录用户进行认证（两个子模块）        | 8888、9100          | msyql、redis    |
| rabbitmq      | 使用rabbitmq工具类生产消费示例            | 8084               | rabbitmq、mysql |
| redis         | 使用redis工具类示例                   | 8085               | redis          |
| sa-token      | 使用satoken示例（三个子模块）             | 8888、9101、9102     | mysql、redis    |
| seata         | seata使用示例（三个子模块）               | 10000、 10001、10002 | seata、mysql    |
| uid-generator | 分布式id使用示例                      | 8086               | mysql          |
| web           | 基于MybatisPlus的orm框架，常见的web开发示例 | 8080               | mysql          |
| web-socket    | websocket的广播单播使用示例             | 8096               | 可直接运行          |

# 快速上手

## 启动项目

1. 拉取脚手架[sakura-boot](https://github.com/yanjingfan/sakura-boot)

2. `maven install`一下**sakura-boot**脚手架各模块
   
   ![image-20210612193432348](docs/pic/image-20210612193432348.png)

3. 拉取demo工程[sakura-boot-demo](https://github.com/yanjingfan/sakura-boot-demo)，启动[gateway服务]()，再启动[web服务]()（会由`flyway`自动生成相关业务表），然后启动其他服务即可（先安装相关中间件），访问`localhost:8888/doc.html`
   
   ![1644311281.png](docs/pic/1644311281.png)

## gitlab自动化部署

此`demo`的`gateway`服务和`web-demo`服务可以在`gitlab`上进行自动化部署，可参考博客：[多模块工程gitlab（CI/CD）自动化部署](https://blog.csdn.net/yanzhenjingfan/article/details/124844630)

## 依赖的中间件

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

### 安装mysql

```shell
#拉取mysql8镜像
docker pull mysql:8.0.21

#运行mysql
docker run -p 3306:3306 --restart=always --name mysql -e MYSQL_ROOT_PASSWORD=admin -d mysql:8.0.21

#进入运行MySQL的docker容器
docker exec -it mysql /bin/bash

#使用MySQL命令打开客户端，密码是启动命令里的MYSQL_ROOT_PASSWORD参数对应的值
mysql -uroot -p

#创建sakura数据库
create database sakura character set utf8mb4;

#给root赋予权限
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%'WITH GRANT OPTION;

#刷新MySQL的系统权限相关表
flush  privileges;
```

我的服务器只有一个g，所以决定修改配置文件进行优化

```shell
# 进入mysql容器
docker exec -it mysql /bin/bash

# 安装vim
apt-get update
apt-get install vim

# 修改配置文件
vim /etc/mysql/conf.d/docker.cnf

# 在docker.cnf里添加下面的配置
performance_schema_max_table_instances=400
table_definition_cache=400
table_open_cache=256
performance_schema=off
```

小插曲儿：centos8上安装mariadb

```sh
# centos8默认安装了podman，用法和docker一样
podman pull mariadb:10.6.3
podman run -p 3306:3306 --restart=always --name mysql -e MYSQL_ROOT_PASSWORD=yangfan -d mariadb:10.6.3
podman exec -it mysql /bin/bash 
```





+ 

### nacos安装

1. nacos启动需要依赖jdk，务必下载并配好jdk8以上版本

2. 从 [最新稳定版本](https://github.com/alibaba/nacos/releases) 下载 `nacos-server-$version.zip` 包。
   
   ```sh
   unzip nacos-server-$version.zip 或者 tar -xvf nacos-server-$version.tar.gz
   ```

3. 修改配置
   
   在`nacos/conf/application.properties`文件放开mysql的配置，配置你自己的数据库信息，如下图
   
   ![image-20210718213943864](docs/pic/image-20210718213943864.png)

4. 运行数据库sql脚本
   
   在第三步配置的数据库中新建nacos库，然后运行`nacos/conf/nacos-mysql.sql`脚本

5. 启动服务器
   
   + Linux/Unix/Mac
     
     启动命令(standalone代表着单机模式运行，非集群模式):
     
     ```sh
     sh startup.sh -m standalone
     ```
   
   + Windows
     
     启动命令(standalone代表着单机模式运行，非集群模式):
     
     ```
     startup.cmd -m standalone
     ```

### PowerJob安装

官方文档：[PowerJob · 语雀 ](https://www.yuque.com/powerjob/guidence/intro)

1. 初始化数据库
   
   ```sql
   CREATE DATABASE IF NOT EXISTS `powerjob-product` DEFAULT CHARSET utf8mb4
   ```

2. 拉取`mongodb`镜像并运行（可选，保存在线日志）
   
   ```shell
   docker pull mongo:4.2.6
   
   docker run -itd --name mongo -p 27017:27017 mongo:4.2.6
   ```

3. 拉取`powerjob-server`镜像并运行，修改`数据库ip`、`账号密码`，`mongodb的ip`即可
   
   ```shell
   docker pull tjqq/powerjob-server:latest
   
   docker run -d \
          --restart=always \
          --name powerjob-server \
          -p 7700:7700 -p 10086:10086 \
          -e TZ="Asia/Shanghai" \
          -e JVMOPTIONS="" \
          -e PARAMS="--spring.profiles.active=product 
           --spring.datasource.core.jdbc-url=jdbc:mysql://192.168.3.13:3306/powerjob-product?useUnicode=true&characterEncoding=UTF-8 
           --spring.datasource.core.username=root 
           --spring.datasource.core.password=yangfan
           --spring.data.mongodb.uri=mongodb://192.168.3.13:27017/powerjob-product" \
          -v ~/docker/powerjob-server:/root/powerjob/server -v ~/.m2:/root/.m2 \
          tjqq/powerjob-server:latest
   ```

4. 访问`http://192.168.3.13:7700`管理页面，注册账号密码登陆即可

![1645076989.jpg at](docs/pic/1645076989.jpg)

5. 将测试应用打包成镜像，运行进行测试（如果不打包成镜像使用本地测试，可以`git`拉取`PowerJob`的工程到本地，运行`PowerJob`，再运行本地测试应用，可参考[本地IDE版](https://www.yuque.com/powerjob/guidence/nyio9g)
