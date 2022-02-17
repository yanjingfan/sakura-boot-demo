> 动态定时任务

**运行前需注意**：

+ 如果不需要使用分布式调度`powerJob-server`服务，可去掉`sakura-cron`模块中的`powerjob`依赖，否则这个`demo`启动报错

+ 需要先将[sakura-boot](https://github.com/yanjingfan/sakura-boot)脚手架下来进行mvn install，将脚手架的打进本地的maven仓库

启动服务后，访问`localhost:8087/doc.html`进行定时任务的crud测试

![image-20211019140830624.png](https://github.com/yanjingfan/sakura-boot-demo/blob/master/docs/pic/image-20211019140830624.png)

效果：每十秒定时执行一次

![image-20211019141002038.png](https://github.com/yanjingfan/sakura-boot-demo/blob/master/docs/pic/image-20211019141002038.png)

1. `pom.xml`中添加依赖
   
   ```xml
   <!-- 动态任务工具类 -->
   <dependency>
       <groupId>com.sakura</groupId>
       <artifactId>sakura-cron</artifactId>
       <version>1.0</version>
   </dependency>
   
   <!-- 公共类包 --->
   <dependency>
       <groupId>com.sakura</groupId>
       <artifactId>sakura-common</artifactId>
       <version>1.0</version>
   </dependency>
   
   <!-- web相关包，sql防盲注，swagger等 -->
   <dependency>
       <groupId>com.sakura</groupId>
       <artifactId>sakura-web</artifactId>
       <version>1.0</version>
   </dependency>
   
   <!-- 数据库相关依赖包 -->
   <dependency>
       <groupId>com.sakura</groupId>
       <artifactId>sakura-db</artifactId>
       <version>1.0</version>
   </dependency>
   ```

2. 在springboot启动类上加需要被扫描的包
   
   ```java
   @EnableJpaRepositories(basePackages = "com.sakura.common.cron")
   @EntityScan(basePackages = "com.sakura.common.cron")
   //使用分布式调度器PowerJob时需要添加
   @EnableScheduling
   ```

3. 配置yml文件
   
   ```yaml
   server :
     port : 8087
     tomcat:
       uri-encoding: UTF-8
   
   ####################################################################################
   ###################################DataSource Config################################
   ####################################################################################
   spring:
     datasource:
       driver-class-name: com.mysql.cj.jdbc.Driver
       url: jdbc:mysql://localhost:3306/sakura?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai
       username: root
       password: admin
   
   #########################################################################################################
   ########################################## powerjob #############################################
   ######################################################################################
   powerjob:
     worker:
       # akka 工作端口，可选，默认 27777
       akka-port: 27777
       # 接入应用名称，用于分组隔离，推荐填写 本 Java 项目名称
       app-name: powerjob
       # 调度服务器地址，IP:Port 或 域名，多值逗号分隔
       server-address: 192.168.3.107:7700
       # 持久化方式，可选，默认 disk
       store-strategy: disk
       # 任务返回结果信息的最大长度，超过这个长度的信息会被截断，默认值 8192
       max-result-length: 4096
       # 单个任务追加的工作流上下文最大长度，超过这个长度的会被直接丢弃，默认值 8192
       max-appended-wf-context-length: 4096
   
   # 需要关闭sql盲注，cron表达式中有*，会导致接口不通过
   security:
     sql:
       enabled: false
   
   ```

4. 工具类
- 添加定时任务
  
  ```java
  sysJobService.addSysJob(sysJob);
  ```
+ 编辑定时任务
  
  ```java
  sysJobService.editSysJob(sysJob);
  ```

+ 根据id删除定时任务
  
  ```java
  sysJobService.deleteSysJobById(jobId);
  ```

+ 查找所有的定时任务
  
  ```java
  List<SysJobPO> jobs = sysJobService.list();
  ```

+ 开启或暂停定时任务
  
  ```java
  sysJobService.startOrStopSysJob(jobId, jobStatus);
  ```
  
  
