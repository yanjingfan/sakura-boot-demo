package com.sakura.cloud.demo1.controller;

import com.sakura.cloud.demo1.dto.UserDTO;
import com.sakura.cloud.demo1.service.RemoteService;
import com.sakura.cloud.demo1.vo.CategoryAppJsonVO;
import com.sakura.common.exception.CloudException;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * 远程接口调用示例
 *
 * @auther YangFan
 * @Date 2021/1/8 13:54
 */

@RestController
@RequestMapping("/sakura")
@Api(value = " 远程接口调用", tags = {" 远程接口调用"})
public class RemoteApiController {

    private static final Logger log = LoggerFactory.getLogger(RemoteApiController.class);

    @Autowired
    RemoteService remoteService;

    @ApiOperation("使用openfiegn跨服务查询APP")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", dataType = "long", required = true, value = "当前页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "long", required = true, value = "每页条数", defaultValue = "10")
    })
    @GetMapping(value = "/apps")
    public CategoryAppJsonVO queryApps(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize) {
        CategoryAppJsonVO categoryAppJsonVO = null;
        log.info("跨服务查询开始了=======================");
        categoryAppJsonVO = remoteService.queryApps(page, pageSize, "");
        return categoryAppJsonVO;
    }

    @ApiOperation("使用restTemplate向远程接口发送GET请求")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", dataType = "long", required = true, value = "当前页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "long", required = true, value = "每页条数", defaultValue = "10")
    })
    @GetMapping(value = "/remote/users")
    public CategoryAppJsonVO queryRemoteUsers(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize) {
        CategoryAppJsonVO remoteDate = null;
        remoteDate = remoteService.getRemoteDate(page, pageSize);
        return remoteDate;

    }

    @ApiOperation("使用restTemplate向远程接口发送POST请求")
    @PostMapping(value = "/remote/users")
    public CommonResult queryRemoteApps(@RequestBody UserDTO userDTO) {
        remoteService.saveUser(userDTO);
        return CommonResult.success();
    }

}
