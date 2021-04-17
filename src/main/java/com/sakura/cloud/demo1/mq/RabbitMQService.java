package com.sakura.cloud.demo1.mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

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

    @RabbitListener(bindings = @QueueBinding(
                    value = @Queue(value = "fanout_queue_sms"),
                    exchange = @Exchange(value = "tm.test.exchange",
                    type = ExchangeTypes.FANOUT)))
    public void psubConsumerSmsAno(Message message, Channel channel) {
        try {
            System.out.println("短信业务接收到消息： " + new String(message.getBody(), "UTF-8"));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (Exception e) {

        }
    }

}
