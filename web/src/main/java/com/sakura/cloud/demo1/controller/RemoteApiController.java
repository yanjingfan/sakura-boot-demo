package com.sakura.cloud.demo1.controller;

import com.sakura.cloud.demo1.dto.UserDTO;
import com.sakura.cloud.demo1.service.RemoteService;
import com.sakura.cloud.demo1.vo.CategoryAppJsonVO;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation("使用openfiegn跨服务查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", dataType = "long", required = true, value = "当前页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "long", required = true, value = "每页条数", defaultValue = "10")
    })
    @GetMapping(value = "/openfeign/users")
    public CommonResult<CategoryAppJsonVO> queryUsers(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize) {
        log.info("跨服务查询开始了=======================");
        CategoryAppJsonVO categoryAppJsonVO = remoteService.queryUsers(page, pageSize);
        return CommonResult.success(categoryAppJsonVO);
    }

    @ApiOperation("使用restTemplate向远程接口发送GET请求")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", dataType = "long", required = true, value = "当前页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "long", required = true, value = "每页条数", defaultValue = "10")
    })
    @GetMapping(value = "/remote/users")
    public CommonResult<CategoryAppJsonVO> queryRemoteUsers(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize) {
        CategoryAppJsonVO remoteDate = remoteService.getRemoteDate(page, pageSize);
        return CommonResult.success(remoteDate);

    }

    @ApiOperation("使用restTemplate发送POST请求-Payload")
    @PostMapping(value = "/remote/users")
    public CommonResult<Object> saveRemoteAppsWithPayload(@RequestBody List<UserDTO> userDTO) {
        remoteService.saveUser(userDTO);
        return CommonResult.success();
    }

    @ApiOperation("使用restTemplate发送POST请求-表单")
    @PostMapping(value = "/remote/formdata/users")
    public CommonResult<Object> saveRemoteAppsWithFormData(UserDTO userDTO) {
        remoteService.saveUserWithFormData(userDTO);
        return CommonResult.success();
    }

}
