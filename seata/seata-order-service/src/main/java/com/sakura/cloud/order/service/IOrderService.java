package com.sakura.cloud.order.service;

import com.sakura.cloud.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangfan
 * @since 2022-06-27
 */
public interface IOrderService extends IService<Order> {
    /**
     * 创建订单
     */
    void create(Order order);
}
