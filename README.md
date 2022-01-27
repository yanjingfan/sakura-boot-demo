# sakura-boot快速上手

## 启动项目

1. 使用ide拉取[yanjingfan/sakura-boot: 基于springboot2.x的微服务脚手架 (github.com)](https://github.com/yanjingfan/sakura-boot)

2. 打开项目，使用maven将各模块的打成jar包，放入本地的maven仓库
   
   ![image-20210612193432348](https://github.com/yanjingfan/sakura-boot-demo/blob/master/docs/pic/image-20210612193432348.png)

3. `sakura-boot-demo`更新maven依赖后，运行`DemoApplication`中`main`方法，访问`localhost:8080/doc.html`测试接口，页面如下
   
   ![20210525164613.png](https://github.com/yanjingfan/sakura-boot-demo/blob/master/docs/pic/20210525164613.png)

## 目录结构说明

```txt
SAKURA-BOOT-DEMO
├─docs            # 项目文件
├─dynamic-cron    # 动态定时任务示例
├─es             # elasticsearch通用查询示例
├─fastdfs        # 使用fastdfs工具类文件上传下载示例
├─gateway        # SpringCloud Gateway网关
├─jpa            # jpa+querydsl常见的crud示例
├─minio            # 使用minio工具类文件上传示例
├─rabbitmq        # 使用rabbitmq工具类生产消费示例
├─redis            # 使用redis工具类示例
├─uid-generator      # 生成唯一id示例
└─web            # 基于MybatisPlus的orm框架，常见的web开发示例
```

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

### Redis安装

```shell
#拉取Redis6.0的docker镜像
docker pull redis:6

#启动Redis服务
docker run -p 6379:6379 --restart=always --name redis -d redis:6 redis-server --appendonly yes
```

### RabbitMQ安装

```shell
#拉取rabbitmq3.7.15的docker镜像
docker pull rabbitmq:3.7.15

#启动RabbitMQ服务
docker run -p 5672:5672 -p 15672:15672 -v /data/rabbitmq/:/var/lib/rabbitmq --restart=always --name rabbitmq -d rabbitmq:3.7.15

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

### minio安装

docker安装

```shell
docker pull minio/minio

# 文件存放在/data/minioData
podman run \
  -d \
  -p 9000:9000 \
  -p 9001:9001 \
   --restart=always \
  --name=minio \
  minio/minio server /data/minioData --console-address ":9001"
```

启动后，访问：ip:9000

默认的账号密码：minioadmin/minioadmin

### nacos安装

1. nacos启动需要依赖jdk，务必下载并配好jdk8以上版本

2. 从 [最新稳定版本](https://github.com/alibaba/nacos/releases) 下载 `nacos-server-$version.zip` 包。
   
   ```sh
   unzip nacos-server-$version.zip 或者 tar -xvf nacos-server-$version.tar.gz
   ```

3. 修改配置
   
   在`nacos/conf/application.properties`文件放开mysql的配置，配置你自己的数据库信息，如下图
   
   ![image-20210718213943864](https://github.com/yanjingfan/sakura-boot-demo/blob/master/docs/pic/image-20210718213943864.png)

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

### filebet+elk安装

1. 拉取镜像
   
   ```shell
   docker pull elasticsearch:7.16.3
   
   docker pull logstash:7.16.3
   
   docker pull elastic/filebeat:7.16.3
   
   # ES的可视化系统（可不安装）
   docker pull kibana:7.16.3
   ```

2. 新建配置
   
   注意：更换这些配置中你自己的ip即可
   
   + 在`/opt/elk/config`目录下新建`filebeat.yml`
     
     ```yml
     filebeat.inputs:
     - type: log
       enabled: true
       paths:
         - /home/logs/nginx/*.log
       fields:
         log_topic: nginx_log
     
     #日志输出配置
     output.logstash:
       hosts: ["192.168.3.13:5044"]
     ```
   
   + 在`/opt/elk/config`目录下新建`logstash.yml`
     
     ```yml
     http.host: "0.0.0.0"
     xpack.monitoring.elasticsearch.hosts: [ "http://192.168.3.13:9200" ]
     ```
   
   + 在`/opt/elk/config`目录下新建`logstash.conf`
     
     ```conf
     input {
       beats {
         port => 5044
       }
     }
     
     filter {
       if [fields][log_topic] == "nginx_log"{
         json {
           source => "message"
           remove_field => ["message"]
         }
         date {
           match => ["time", "yyyy-MM-dd HH:mm:ss ZZ", "ISO8601"]
           target => "@timestamp"
         }
       }
     }
     
     output {
       stdout{ codec=>rubydebug}
       if [fields][log_topic] == "nginx_log"{
               elasticsearch {
                 hosts => ["192.168.3.13:9200"]
                  index => "nginx_log-%{+YYYY.MM.dd}"
               }
       }
     
     }
     ```
   
   + 在`/opt/elk/config`目录下新建`elasticsearch.yml`
     
     ```yml
     cluster.name: "elasticsearch"
     network.host: 0.0.0.0
     #快照仓库地址
     #path.repo: /opt/elk/es/snapshot
     ```

3. 启动服务
   
   + 启动`filebeat`，将需要监控的日志目录挂载进`filebeat`容器中
     
     ```shell
     docker run -d --name=filebeat \
     --user=root \
     -v /opt/elk/config/filebeat.yml:/usr/share/filebeat/filebeat.yml \
     -v /home/logs/nginx/:/home/logs/nginx/ \
     elastic/filebeat:7.16.3 
     ```
   
   + 启动`logstash`
     
     ```shell
     docker run -d --name=logstash -p 5044:5044 -p 9600:9600 \
     -v /opt/elk/config/logstash.yml:/usr/share/logstash/config/logstash.yml \
     -v /opt/elk/config/logstash.conf:/usr/share/logstash/pipeline/logstash.conf \
     --log-opt max-size=100m \
     logstash:7.16.3 
     ```
   
   + 启动`ElasticSearch`
     
     ```shell
     docker run -d --name es -p 9200:9200 -p 9300:9300 \
     -e "discovery.type=single-node" -e "cluster.name=elasticsearch" \
     -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" \
     -v /opt/elk/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
     --log-opt max-size=100m -d elasticsearch:7.16.3
     ```
   
   + 启动`kibana`
     
     ```shell
     docker run -d -p 5601:5601 \
      --name kibana \
      -e ELASTICSEARCH_HOSTS=http://192.168.3.13:9200 \
      --log-opt max-size=100m kibana:7.16.3
     ```

4. 造假数据
   
   在`/home/logs/nginx`目录下新建一个`test.log`日志文件，内容为`json`格式的日志：
   
   ```text
   {"file":"go:210","level":"info","msg":"你好1","time":"2022-01-10T11:20:32+08:00"}
   {"file":"go:220","level":"warn","msg":"你好2","time":"2022-01-15T11:20:32+08:00"}
   {"file":"go:230","level":"error","msg":"你好3","time":"2022-01-22T11:20:32+08:00"}
   {"file":"go:240","level":"info","msg":"你好4","time":"2022-01-24T11:20:32+08:00"}
   ```

5. 这些日志就会从`filebeat`=>`logstash`=>`ElasticSearch`=>`kibana`，可以在`kibana`看到这些日志数据
   
   这时候引入`sakura-es`模块，进行简单查询了，无需编写代码，一步到位。
   
   接口请求如图：
   
   ![请求示例](https://github.com/yanjingfan/sakura-boot-demo/blob/master/docs/pic/Snipaste_2022-01-27_10-25-48.png)
