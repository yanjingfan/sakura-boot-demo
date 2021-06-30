package com.sakura.cloud.controller;

import com.sakura.cloud.service.RabbitMQService;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @auther YangFan
 * @Date 2021/1/18 16:10
 */

@RestController
@RequestMapping("/sakura")
@Api(value = "mq发布订阅", tags = {"mq发布订阅"})
public class RabbitmqController {

    private static final Logger log = LoggerFactory.getLogger(RabbitmqController.class);

    @Resource
    RabbitMQService rabbitMQService;

    @ApiOperation("消息发布")
    @PostMapping("/publish")
    public CommonResult<String> publish() throws Exception {
        rabbitMQService.publish();
        return CommonResult.success("保存到数据库并且成功将相关信息发送到mq!");

    }

    @ApiOperation("消息订阅")
    @PostMapping("/subscribe")
    public CommonResult<String> subscribe() throws Exception {
        rabbitMQService.subscribe();
        return CommonResult.success("消息成功订阅!");
    }
}
