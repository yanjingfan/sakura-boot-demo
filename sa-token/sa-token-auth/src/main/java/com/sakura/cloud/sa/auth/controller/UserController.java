package com.sakura.cloud.sa.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.sakura.cloud.sa.auth.service.IUserService;
import com.sakura.cloud.sa.auth.vo.UserVO;
import com.sakura.common.domian.UserDTO;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "用户管理接口", tags = {"用户管理接口"})
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @ApiOperation(value = "账号密码登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult<SaTokenInfo> login(@Valid @RequestBody UserDTO dto) {
        SaTokenInfo saTokenInfo = userService.pcLogin(dto);
        if (saTokenInfo == null) {
            return CommonResult.unauthorized("用户名或密码错误");
        }
        return CommonResult.success(saTokenInfo);
    }

    @ApiOperation(value = "登出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public CommonResult loginOut(@ApiParam("PC：pc端登出;WX：小程序登出") @RequestParam String loginDevice) {
        userService.loginOut(loginDevice);
        return CommonResult.success();
    }

    @ApiOperation(value = "账号注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult<UserVO> register(@Valid @RequestBody UserDTO dto) {
        UserVO user = userService.register(dto);
        return CommonResult.success(user);
    }
}
