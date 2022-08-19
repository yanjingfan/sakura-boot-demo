package com.sakura.cloud.demo1.controller;

import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试类
 *
 *
 * 通过/log/{level}/{pkn}接口改变日志等级后，这里查看对应的日志输出
 *
 * @auther YangFan
 * @Date 2020/12/28 9:52
 */

@RestController
@RequestMapping("/sakura")
@Api(value = "改变日志等级后的日志测试输出", tags = {"改变日志等级后的日志测试输出"})
public class LogChangeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogChangeController.class);

    @ApiOperation("不同等级日志输出测试")
    @GetMapping("/log/level/print")
    public CommonResult<String> simple() {
        LOGGER.debug("这是一个debug日志...");
        LOGGER.info("这是一个info日志...");
        LOGGER.error("这是一个error日志...");
        return CommonResult.success("请回到查看控制台的日志输出等级");
    }
}
