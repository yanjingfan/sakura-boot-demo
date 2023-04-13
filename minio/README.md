# Minio部署手册

推荐一看：【[使用MinIO构建分布式文件系统来一次分布式文件系统实战！](https://www.jianshu.com/p/bbd6d17cb6a9)】

## 单机服务端安装

### docker安装

```shell
docker pull minio/minio


mkdir -p /home/minio/data

# 文件存放在/data/minioData
docker run \
  -d \
  --net=host \
  -e MINIO_ACCESS_KEY=sinvie \
  -e MINIO_SECRET_KEY=sinvie@123 \
  -p 9000:9000 \
  -p 9001:9001 \
  -v /home/minio/data:/home/minio/data \
  --restart=always \
  --name=minio \
  minio/minio server /data/minioData --console-address ":9001"
```



### 源码安装

1. 进入`/usr/local/src`目录下
   
   ```shell
   cd /usr/local/src
   ```

2. 下载MinIO Client
   
   ```shell
   wget http://dl.minio.org.cn/server/minio/release/linux-amd64/minio
   ```

3. 修改权限
   
   ```shell
   chmod +x minio
   ```

4. 启动服务端
   
   ```shell
   ./minio server /data/minioData
   ```
   
   正常启动后的日志打印
   
   ```shell
   [root@racknerd-a1ef27 src]# ./minio server /data/minioData
   No credential environment variables defined. Going with the defaults.
   It is strongly recommended to define your own credentials via environment variables MINIO_ROOT_USER and MINIO_ROOT_PASSWORD instead of using default values
   Endpoint: http://216.240.130.167:9000  http://172.17.0.1:9000  http://127.0.0.1:9000               
   RootUser: minioadmin 
   RootPass: minioadmin 
   
   Browser Access:
      http://216.240.130.167:9000  http://172.17.0.1:9000  http://127.0.0.1:9000              
   
   Command-line Access: https://docs.min.io/docs/minio-client-quickstart-guide
      $ mc alias set myminio http://216.240.130.167:9000 minioadmin minioadmin
   
   Object API (Amazon S3 compatible):
      Go:         https://docs.min.io/docs/golang-client-quickstart-guide
      Java:       https://docs.min.io/docs/java-client-quickstart-guide
      Python:     https://docs.min.io/docs/python-client-quickstart-guide
      JavaScript: https://docs.min.io/docs/javascript-client-quickstart-guide
      .NET:       https://docs.min.io/docs/dotnet-client-quickstart-guide
   Detected default credentials 'minioadmin:minioadmin', please change the credentials immediately by setting 'MINIO_ROOT_USER' and 'MINIO_ROOT_PASSWORD' environment values
   ```

5. 测试
   
   通过上面第三步的启动日志，可以看到账号密码，以及浏览器访问的地址
   
   访问：http://216.240.130.167:9000
   
   账号/密码：`minioadmin` / `minioadmin`
   


### 设置启动脚本

创建脚本

```sh
vim minio.sh
```

新建`logs`目录

```shell
mkdir /usr/local/src/logs
```

启动脚本内容

```shell
#!/bin/bash
# 账号
export MINIO_ACCESS_KEY=username
# 密码
export MINIO_SECRET_KEY=password
# 端口设置为9000，数据文件存放在/data/minioData
nohup /usr/local/src/minio server --address=0.0.0.0:9000 /data/minioData > /usr/local/src/logs/minio.log 2>&1&
```

## 客户端安装（可选）

1. 进入`/usr/local/src`目录下
   
   ```shell
   cd /usr/local/src
   ```

2. 下载MinIO Client
   
   ```shell
   wget http://dl.minio.org.cn/client/mc/release/linux-amd64/mc
   ```

3. 权限分配
   
   ```shell
   chmod +x mc
   ```

4. 添加MinIO存储服务
   
   MinIO服务器显示URL，访问权和秘密密钥。
   
   #### 用法
   
   ```shell
   ./mc config host add myminio http://xxx.xxx.xxx.xxx:9000 access-key  secret-key [--api API-SIGNATURE]
   ```
   
   别名就是给你的云存储服务起了一个短点的外号。S3 endpoint,access key和secret key是你的云存储服务提供的。API签名是可选参数，默认情况下，它被设置为"S3v4"。
   
   ### 例子
   
   ```linux
   [root@racknerd-a1ef27 src]# ./mc config host add minio http://216.240.130.167:9000 username password
   Added `minio` successfully.
   ```
   
   查看服务器文件桶
   
   ```shell
   [root@racknerd-a1ef27 src]# ./mc ls minio
   [2021-06-19 14:51:40 CST]     0B test/
   ```

## docker集群安装minio

**注意**：minio至少需要四个节点，或者四个driver。说人话就是一台服务至少需要四个存储磁盘，或者两台服务器，每台服务器两个存储磁盘。

1、两个节点执行以下命令

```shell
cat >> /etc/hosts << EOF
192.168.1.189 minio-1
192.168.1.183 minio-2
EOF
```

2、两个节点都要创建数据卷

```shell
docker volume create minio-data1
docker volume create minio-data2

docker volume ls // 查看所有容器卷
# docker volume minio-data2 // 查看指定容器卷详情信息
# docker stop minioyy // 暂停容器实例
# docker rm minioyy // 移除容器实例
# docker volume rm minio-data2 // 删除自定义数据卷
```

3、配置时间同步，关闭防火墙和selinux

4、189节点执行

```shell
docker run -d --name minio \
  --restart=always --net=host \
  -e MINIO_ACCESS_KEY=sinvie \
  -e MINIO_SECRET_KEY=sinvie@123 \
  -v minio-data1:/data1 \
  -v minio-data2:/data2 \
  minio/minio server \
  --address 192.168.1.189:9000 \
  http://minio-{1...2}/data{1...2}
```

5、183节点执行

```shell
docker run -d --name minio \
  --restart=always --net=host \
  -e MINIO_ACCESS_KEY=sinvie \
  -e MINIO_SECRET_KEY=sinvie@123 \
  -v minio-data1:/data1 \
  -v minio-data2:/data2 \
  minio/minio server \
  --address 192.168.1.183:9000 \
  http://minio-{1...2}/data{1...2}
```

6、访问192.168.1.183:9000，账号密码：sinvie/sinvie@123

## 扩容

MinIO的两种水平扩容方式：对等扩容和联邦扩容，这里只简单介绍对等扩容。

### 对等扩容

> MinIO并不支持向集群中添加单个节点的扩容方式，因为加入单个节点后，会为整个集群带来复杂的调度和处理过程，并不利于维护。因此，MinIO提供了一种对等扩容的方式，即增加的节点数和磁盘数均需与原集群保持对等。

#### 优点

在于配置操作简单易行，通过一条命令即可完成扩容（注意：推荐使用连续的节点IP，并参照MinIO官网在扩容命令中使用{}）。

#### 局限性

①扩容需重启；

②扩容存在限制，集群节点数一般不超过32个，这是由于MinIO集群通过分布式锁保证强一致性，若集群节点数过大，维护强一致性将带来性能问题
