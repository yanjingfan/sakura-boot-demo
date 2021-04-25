package com.sakura.cloud.demo1.service;

/**
 * @auther YangFan
 * @Date 2021/1/19 15:58
 */
public interface OrderService {

    void saveOrderAndPublish() throws Exception;

    void subscribe();
}
