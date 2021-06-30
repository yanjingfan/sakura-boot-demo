package com.sakura.cloud.service.impl;

import com.sakura.cloud.service.RabbitMQService;
import com.sakura.common.exception.YErrorException;
import com.sakura.common.mq.api.EventBusHelper;
import com.sakura.common.mq.api.support.binding.DefaultDestination;
import com.sakura.common.mq.api.support.binding.ExchangeType;
import com.sakura.common.mq.api.support.message.DefaultEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @auther YangFan
 * @Date 2021/1/19 15:59
 */

@Service
public class RabbitMQServiceImpl implements RabbitMQService {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQServiceImpl.class);

    /**
     * 保存订单，并将相关数据发送到mq
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publish() {
        String orderId = UUID.randomUUID().toString();

        //填写消息的相关配置和内容
        DefaultDestination destination = DefaultDestination.builder()
                .exchangeName("tm.test.exchange")   //交换机名
                .queueName("tm.test.queue")         //发布订阅模式时，队列名可为空
                .routingKey("")                     //路由键
                .exchangeType(ExchangeType.FANOUT)  //交换机类型
                .build();

        DefaultEvent defaultEvent = DefaultEvent.builder()
                .businessKey(orderId)               //业务id
                .source("SAVE_ORDER")               //事件源（模块名）
                .sourceData("我生产了一条信息")      //事件内容
                .build();

        try {
            //事件发布
            EventBusHelper.publish(destination, defaultEvent);
        } catch (Exception e) {
            throw new YErrorException("事件发布出错!");
        }
        log.info("保存订单:{}成功...", orderId);
    }

    /**
     * 非发布订阅模式，队列名要和生产者的队列名一致，否则消费不到
     *
     * 事件消息消费，从mq中获取到订单信息
     */
    @Override
    public void subscribe() {
        String queueName = "tm.test.queue";
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

}
