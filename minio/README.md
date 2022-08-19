minio安装

```shell
docker pull minio/minio

mkdir -p /data/minioData

# 文件存放在/data/minioData
docker run \
  -d \
  -p 9000:9000 \
  -p 9001:9001 \
   --restart=always \
  --name=minio \
  minio/minio server /data/minioData --console-address ":9001"
```

启动后，访问：ip:9000

默认的账号密码：minioadmin/minioadmin
