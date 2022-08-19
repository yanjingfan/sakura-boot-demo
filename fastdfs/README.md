> `fastdfs`中间件安装

- 拉取镜像
  
  ```shell
  docker pull qbanxiaoli/fastdfs
  ```

- 启动fastdfs
  
  ```shell
  docker run -d --restart=always --privileged=true --net=host --name=fastdfs -e IP=192.168.1.130 qbanxiaoli/fastdfs
  ```

- 测试fastdfs是否搭建成功
  
  ```shell
  docker exec -it fastdfs /bin/bash
  
  echo "Hello FastDFS!">index.html
  
  fdfs_test /etc/fdfs/client.conf upload index.html
  ```

- 访问返回来的url，可以访问就表示搭建成功
  
  ![image-20210425215816914](D:\yangfan\mdpic\86b84f06e7f8d7fbc5872f779aa53b9eed30d369.png)
+ 访问返回来的url，可以访问就表示搭建成功
  
  ![image-20210425220038541](D:\yangfan\mdpic\d00028bcd59c9da1034267e6a96124e8bbb87d51.png)



> fastdfs文件上传下载

1. 添加依赖
   
   ```xml
   <dependency>
       <groupId>com.sakura</groupId>
       <artifactId>sakura-file-util</artifactId>
       <version>1.0</version>
   </dependency>
   ```

2. 在springboot启动类上加
   
   ```java
   @Import(FdfsClientConfig.class)
   @EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
   ```

3. 配置yml文件
   
   ```yaml
   spring:  
     servlet:
       multipart:
         max-file-size: 100MB # 最大支持文件大小
         max-request-size: 100MB # 最大支持请求大小
   
   fdfs:
     # socket连接超时时长
     soTimeout: 1500
     # 连接tracker服务器超时时长
     connectTimeout: 600
     pool:
       # 从池中借出的对象的最大数目
       max-total: 153
       # 获取连接时的最大等待毫秒数100
       max-wait-millis: 102
     # 缩略图生成参数，可选
     thumbImage:
       width: 150
       height: 150
     # 跟踪服务器tracker_server请求地址,支持多个，这里只有一个，如果有多个在下方加- x.x.x.x:port
     trackerList:
       - 192.168.1.130:22122
     # 存储服务器storage_server访问地址
     web-server-url: http://192.168.1.130/
   ```

4. 工具类 [FastDFSClient](https://github.com/yanjingfan/boot-parent/blob/master/sakura-file-util/src/main/java/com/sakura/common/fastdfs/FastDFSClient.java)
   
   + 上传文件对象
     
     ```java
     String str = FastDFSClient.uploadFile(file);
     ```
   
   + 上传缩略图
     
     ```java
     String str = FastDFSClient.uploadImageAndCrtThumbImage(MultipartFile multipartFile);
     ```
   
   + 下载文件
     
     ```java
     boolean downloadFile(String fileUrl, File file);
     ```
   
   + 删除文件
     
     ```java
     boolean deleteFile(String fileUrl);
     ```

5. 使用示例
   
   ```java
   @Test
   public void Upload() {
       String fileUrl = this.getClass().getResource("/fileUpload/redis-M-S.png").getPath();
       File file = new File(fileUrl);
       String str = FastDFSClient.uploadFile(file);
       FastDFSClient.getResAccessUrl(str);
   }
   
   @Test
   public void Delete() {
       //上传附件之后返回的url
       FastDFSClient.deleteFile("group1/M00/00/00/wKgjDGA4Zl2ARQNYAABZAfzrN84686.png");
   }
   ```
