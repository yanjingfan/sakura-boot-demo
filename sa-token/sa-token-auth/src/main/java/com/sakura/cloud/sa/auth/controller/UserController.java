package com.sakura.cloud.sa.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.sakura.cloud.sa.auth.service.UserServiceImpl;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义Oauth2获取令牌接口
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @ApiOperation(value = "账号密码登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult<Map<String, String>> login(@RequestParam String username, @RequestParam String password) {
        SaTokenInfo saTokenInfo = userService.pcLogin(username, password);
        if (saTokenInfo == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", saTokenInfo.getTokenValue());
        tokenMap.put("tokenHead", saTokenInfo.getTokenName());
        return CommonResult.success(tokenMap);
    }

    // 查询 Token 信息  ---- http://localhost:8081/acc/tokenInfo
    @RequestMapping("tokenInfo")
    public CommonResult<SaTokenInfo> tokenInfo() {
        return CommonResult.success(StpUtil.getTokenInfo());
    }

    @ApiOperation(value = "登出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public CommonResult loginOut(@ApiParam("PC：pc端登出;WX：小程序登出") @RequestParam String loginDevice) {
        userService.loginOut(loginDevice);
        return CommonResult.success();
    }
}
