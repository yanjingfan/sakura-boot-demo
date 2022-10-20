package com.sakura.cloud.sa.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sakura.cloud.sa.auth.dto.UpdateUserPassWordDTO;
import com.sakura.cloud.sa.auth.dto.UserPageDTO;
import com.sakura.cloud.sa.auth.entity.Role;
import com.sakura.cloud.sa.auth.service.IUserService;
import com.sakura.cloud.sa.auth.vo.UserVO;
import com.sakura.common.db.mp.CommonPage;
import com.sakura.common.domian.UserDTO;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        return CommonResult.success(saTokenInfo);
    }

    @ApiOperation(value = "账号登出")
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

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public CommonResult<CommonPage<UserVO>> userPage(@RequestBody UserPageDTO dto) {
        IPage<UserVO> users = userService.userPage(dto);
        return CommonResult.success(CommonPage.restPage(users));
    }

    @ApiOperation("获取指定用户信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult<UserVO> getUser(@PathVariable Long id) {
        UserVO user = userService.getUser(id);
        return CommonResult.success(user);
    }

    @ApiOperation("修改指定用户信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id, @RequestBody UserDTO dto) {
        userService.updateUser(id, dto);
        return CommonResult.success();
    }

    @ApiOperation("删除指定用户信息")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public CommonResult delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return CommonResult.success();
    }

    @ApiOperation("用户自行修改密码")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePassword(@Validated @RequestBody UpdateUserPassWordDTO dto) {
        userService.updatePassword(dto);
        return CommonResult.success();
    }

    @ApiOperation("修改帐号状态")
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        userService.updateStatus(id, status);
        return CommonResult.success();
    }

    @ApiOperation("给用户分配角色")
    @RequestMapping(value = "/role/update/{userId}", method = RequestMethod.POST)
    public CommonResult updateRole(@PathVariable Long userId, @RequestBody List<Long> roleIds) {
        userService.updateRole(userId, roleIds);
        return CommonResult.success();
    }

    @ApiOperation("获取指定用户的角色")
    @RequestMapping(value = "/role/{userId}", method = RequestMethod.GET)
    public CommonResult<List<Role>> getRoleList(@PathVariable Long userId) {
        List<Role> roleList = userService.getRoleList(userId);
        return CommonResult.success(roleList);
    }
}
