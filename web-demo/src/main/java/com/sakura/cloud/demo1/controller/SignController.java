package com.sakura.cloud.demo1.controller;

import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther yangfan
 * @date 2023/2/6
 * @describle
 */
@Slf4j
@RestController
@RequestMapping("/sign")
@Api(value = "安全接口", tags = {"安全接口"})
public class SignController {

    @GetMapping(value = "/users")
    public CommonResult<Object> queryUsers() {
        log.info("开始查询了==1111111");
        return CommonResult.success();
    }
}
