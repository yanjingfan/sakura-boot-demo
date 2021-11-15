> 动态定时任务

注意：需要先将[sakura-boot](https://github.com/yanjingfan/sakura-boot)脚手架下来进行mvn install，将脚手架的打进本地的maven仓库

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
   
   <!-- 初始化定时任务相关表所需要包依赖 -->
   <dependency>
       <groupId>com.sakura</groupId>
       <artifactId>sakura-flyway</artifactId>
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
   ########################################## flyway #############################################
   ######################################################################################
     flyway:
       enabled: true
       # 迁移前校验 SQL 文件是否存在问题
       validate-on-migrate: true
       # 生产环境一定要关闭
       clean-disabled: true
       # 校验路径下是否存在 SQL 文件
       check-location: false
       # 最开始已经存在表结构，且不存在 flyway_schema_history 表时，需要设置为 true
       baseline-on-migrate: true
       # 基础版本 0
       baseline-version: 0
   
   # 需要关闭sql盲注，cron表达式中有*，会导致接口不通过
   security:
     sql:
       enabled: false
   ```

4. 工具类

   + 添加定时任务

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