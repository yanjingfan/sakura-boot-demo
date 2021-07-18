加入依赖

```xml
<parent>
    <groupId>com.sakura</groupId>
    <artifactId>sakura-mq</artifactId>
    <version>1.0</version>
</parent>
```

引入此模块需要在`application.yml`添加`rabbitmq`和`flyway`的相关配置

+ 可以根据具体情况，新建mq的用户、密码、虚拟主机

```yaml
####################################################################################
###################################DataSource Config################################
####################################################################################
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://216.240.130.167:3306/sakura?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: admin
#########################################################################################################
##########################################spring boot rabbitmq #############################################
#########################################################################################################
  rabbitmq:
    host: 216.240.130.167
    port: 5672
    virtual-host: /sakura
    username: sakura
    password: sakura
    listener:
      simple:
        acknowledge-mode: manual #开启手动Ack。消息默认设置为自动ack，这种情况下，MQ只要确认消息发送成功，无须等待应答就会丢弃消息
        prefetch: 2

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
```



发布和订阅：

1、注入事件操作类

```java
@Autowired
private Event event;
```



2、事件发布

```java
//填写事件的相关配置和内容
DefaultDestination destination = DefaultDestination.builder()
    .exchangeName("tm.test.exchange")   //交换机名
    .queueName("tm.test.queue")         //队列名
    .routingKey("tm.test.key")          //路由键
    .exchangeType(ExchangeType.FANOUT)  //交换机类型
    .build();

DefaultEvent defaultEvent = DefaultEvent.builder()
    .businessKey(orderId)               //业务id
    .source("SAVE_ORDER")               //事件源（模块名）
    .sourceData(content)                //事件内容
    .build();

//事件发布
event.publish(destination, defaultEvent);
```



3、事件消息消费

+ a、订阅模式示例：

  ```java
  /**
   * 使用注解模式实现订阅消息
   *
   * rabbitmq如何做到消息不重复不丢失即使服务器重启
   *  1.exchange持久化（autoDelete属性默认为false，默认持久化）
   *  2.queue持久化（autoDelete属性默认为false，默认持久化）
   *  3.发送消息设置MessageDeliveryMode.persisent这个也是默认的行为
   *  4.手动确认
   *
   * @auther YangFan
   * @Date 2021/1/22 17:04
   */
  
  @Service
  public class RabbitMQService {
  
      /**
       * 1：当Queue中的 autoDelete 属性被设置为true时,
       *    那么，当消息接收着宕机，关闭后，消息队列则会删除.
       *    消息发送者一直发送消息，当消息接收者重新启动恢复正常后，会接收最新的消息，而宕机期间的消息则会丢失
       *
       * @param message
       * @param channel
       */
      @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "fanout_queue_email", autoDelete = "false"),//队列名自定义
                      exchange = @Exchange(value = "tm.test.exchange",//这个交换机名称要和发布时的交换机名称相同
                              type = ExchangeTypes.FANOUT))) //类型为发布订阅模式
      public void psubConsumerEmailAno(Message message, Channel channel) {
          try {
              /**
               * basicQos(prefetchSize, prefetchCount, prefetchCount)
               * prefetchSize：服务器传送最大内容量（以八位字节计算），如果没有限制，则为0
               * prefetchCount：会告诉RabbitMQ不要同时给一个消费者推送多于N个消息，即一旦有N个消息还没有ack，则该consumer将block掉，直到有消息ack
               * global：true\false 是否将上面设置应用于channel，简单点说，就是上面限制是channel级别的还是consumer级别
               *
               */
              channel.basicQos(0, 5, false);
              System.out.println("邮件业务接收到消息： "+ new String(message.getBody(), "UTF-8"));
  
              /**
               * 无异常就手动确认消息
               *    basicAck(long deliveryTag, boolean multiple)
               *    deliveryTag:取出来当前消息在队列中的的索引;
               *    multiple:为true的话就是批量确认,如果当前deliveryTag为5,那么就会确认
               *    deliveryTag为5及其以下的消息;一般设置为false
               */
              channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
          } catch (Exception e) {
  
              /**
               * 方案一：
               *      根据异常类型来选择是否重新放入队列
               *      basicNack(long deliveryTag, boolean multiple, boolean requeue)
               *      requeue:true为将消息重返当前消息队列,还可以重新发送给消费者;false:将消息丢弃
               *
               *      消息会不断发送，直到消息成功被消费，容易造成死循环
               *
               */
              /*try {
                  channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
              } catch (IOException e1) {
                  e1.printStackTrace();
              }*/
  
              /**
               * 方案二：
               *    先成功确认，然后通过channel.basicPublish()重新发布这个消息
               *
               */
  
              /**
               * 方案三：
               *    记录日志，记录消费失败的信息，数据定时调度，补偿消费
               *
               */
          }
      }
  }
  ```

  

+ b、此消费api的队列名要和生产的队列名保持一致，并且只能一次性消费

  代码示例：

  ```java
  /**
   * 事件消息消费，从mq中获取到订单信息
   */
  @Override
  public void subscribe() {
      String queueName = "tm.test.queue2";
      String exchangeName = "tm.test.exchange";
      try {
          String subscribe = EventBusHelper.subscribe(queueName, data -> {
              System.err.println("111========" + data);
              return true;
          });
          System.err.println("=============" + subscribe);
      } catch (Exception e) {
          throw new YErrorException("事件订阅出错!");
      }
  }
  ```

  