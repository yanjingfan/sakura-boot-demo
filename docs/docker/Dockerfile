#基础镜像
FROM 192.168.1.130:5000/library/centos-jdk:1.8.25

#作者
MAINTAINER 1314216759@163.com

#添加文件
ADD target/sakura-demo-1.0.0-SNAPSHOT.jar /usr/local

#执行命令，主要用来安装相关的软件
RUN chmod u+x /usr/local/sakura-demo-1.0.0-SNAPSHOT.jar

#挂载目录到容器
#VOLUME ["/data"]

#环境变量设置
ENV JAVA_OPTS="-Xms2048m -Xmx2048m -XX:PermSize=256M -XX:MaxPermSize=256M"

#开放端口
EXPOSE 8080

#启动时执行的命令
CMD ["/bin/bash"]


ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /usr/local/sakura-demo-1.0.0-SNAPSHOT.jar"]
