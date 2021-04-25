package com.sakura.cloud.demo1.controller;

import com.sakura.cloud.demo1.service.OrderService;
import com.sakura.common.exception.CloudException;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther YangFan
 * @Date 2021/1/18 16:10
 */

@RestController
@RequestMapping("/sakura")
@Api(value = "mq发布订阅", tags = {"mq发布订阅"})
public class RabbitmqController {

    private static final Logger log = LoggerFactory.getLogger(RabbitmqController.class);

    @Autowired
    OrderService orderService;

    @ApiOperation("保存订单消息发布")
    @PostMapping("/saveOrderAndPublish")
    public CommonResult saveOrderAndPublish() throws Exception {
        orderService.saveOrderAndPublish();
        return CommonResult.success("保存到数据库并且成功将相关信息发送到mq!");

    }

    @ApiOperation("消息订阅")
    @PostMapping("/subscribe")
    public CommonResult subscribe() throws Exception {
        orderService.subscribe();
        return CommonResult.success("消息成功订阅!");
    }

    @ApiOperation("消息订阅2")
    @PostMapping("/subscribe2")
    public CommonResult subscribe2() throws Exception {
        orderService.subscribe2();
        return CommonResult.success("消息成功订阅!");
    }
}
