package com.sakura.cloud.order.controller;


import com.sakura.cloud.order.entity.Order;
import com.sakura.cloud.order.service.IOrderService;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangfan
 * @since 2022-06-27
 */
@RestController
@RequestMapping("/order")
@Api(value = "订单操作API", tags = {"订单操作API"})
public class OrderController {
    @Autowired
    private IOrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping("/create")
    @ApiOperation("创建订单")
    public CommonResult create(Order order) {
        orderService.create(order);
        return CommonResult.success("订单创建成功!");
    }
}

