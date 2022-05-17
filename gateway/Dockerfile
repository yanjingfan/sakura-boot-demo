# 该镜像需要依赖的基础镜像
FROM openjdk:8-jdk-alpine
# 将当前目录下的jar包复制到docker容器的/目录下
ADD gateway.jar /gateway.jar

# 声明服务运行在8080端口
#EXPOSE 8099

#环境变量设置
ENV JAVA_OPTS="-Xms512m -Xmx512m -XX:PermSize=256M -XX:MaxPermSize=256M"
# 指定docker容器启动时运行jar包
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /gateway.jar"]
# 指定维护者的名字
MAINTAINER yangfan