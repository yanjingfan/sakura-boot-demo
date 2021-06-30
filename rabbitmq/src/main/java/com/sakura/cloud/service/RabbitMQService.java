package com.sakura.cloud.service;

/**
 * @auther YangFan
 * @Date 2021/1/19 15:58
 */
public interface RabbitMQService {

    void publish() throws Exception;

    void subscribe();
}
