package com.sakura.cloud.order.service.impl;

import com.sakura.cloud.order.entity.Order;
import com.sakura.cloud.order.mapper.OrderMapper;
import com.sakura.cloud.order.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangfan
 * @since 2022-06-27
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
