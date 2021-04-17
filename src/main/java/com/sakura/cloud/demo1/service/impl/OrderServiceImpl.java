package com.sakura.cloud.demo1.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakura.cloud.demo1.entity.OrderPO;
import com.sakura.cloud.demo1.mapper.OrderMapper;
import com.sakura.cloud.demo1.service.OrderService;
import com.sakura.common.exception.YErrorException;
import com.sakura.common.mq.api.EventBus;
import com.sakura.common.mq.api.EventBusHelper;
import com.sakura.common.mq.api.support.binding.DefaultDestination;
import com.sakura.common.mq.api.support.binding.ExchangeType;
import com.sakura.common.mq.api.support.message.DefaultEvent;
import com.sakura.common.mq.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @auther YangFan
 * @Date 2021/1/19 15:59
 */

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventBus eventBus;

    /**
     * 保存订单，并将相关数据发送到mq
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrderAndPublish() {
        String orderId = UUID.randomUUID().toString();
        BigDecimal amount = BigDecimal.valueOf(100L);

        OrderPO orderPO = new OrderPO();
        orderPO.setUserId(1l);
        orderPO.setOrderId(orderId);
        orderPO.setAmount(amount);
        try {
            orderMapper.insert(orderPO);
        } catch (Exception e) {
            throw new YErrorException("保存订单出错!");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String content = null;
        try {
            content = objectMapper.writeValueAsString(orderPO);
        } catch (JsonProcessingException e) {
            throw new YErrorException("将订单对象转字符串报错!");
        }

        //填写消息的相关配置和内容
        DefaultDestination destination = DefaultDestination.builder()
                .exchangeName("tm.test.exchange")   //交换机名
                .queueName("")                      //队列名
                .routingKey("")                     //路由键
                .exchangeType(ExchangeType.FANOUT)  //交换机类型
                .build();

        DefaultEvent defaultEvent = DefaultEvent.builder()
                .businessKey(orderId)               //业务id
                .source("SAVE_ORDER")               //事件源（模块名）
                .sourceData(content)                //事件内容
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

    /**
     * 事件消息消费，从mq中获取到订单信息2
     */
    @Override
    public void subscribe2() {
        String queueName = "tm.test.queue2";
        String exchangeName = "tm.test.exchange";
        try {
            String subscribe = eventRepository.subscribe2(exchangeName, queueName);
            System.err.println("=============" + subscribe);
        } catch (Exception e) {
            throw new YErrorException("事件订阅出错!");
        }
    }


}
