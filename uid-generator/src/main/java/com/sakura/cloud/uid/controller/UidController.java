package com.sakura.cloud.uid.controller;

import com.github.wujun234.uid.UidGenerator;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yangfan
 * @date 2021/5/10 22:44
 * @description 百度的生成唯一id的demo
 * @modified By
 */

@Slf4j
@RestController
@RequestMapping("/sakura")
@Api(value = "生成唯一id接口", tags = {"生成唯一id接口"})
public class UidController {

    @Resource
    private UidGenerator cachedUidGenerator;

    @ApiOperation("生成唯一id")
    @GetMapping(value = "/uid")
    public CommonResult<Long> saveUser() {
        // Generate UID
        long uid = cachedUidGenerator.getUID();

        //359284080640000、359284634288128、359284919500800、13128615512260612
        log.info("uid=================: {}", uid);

        // Parse UID into [Timestamp, WorkerId, Sequence]
        // {"UID":"450795408770","timestamp":"2019-02-20 14:55:39","workerId":"27","sequence":"2"}
        log.info(cachedUidGenerator.parseUID(uid));
        return CommonResult.success(uid);
    }
}
