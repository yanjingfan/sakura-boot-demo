package com.sakura.cloud.demo1.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sakura.cloud.demo1.dto.UserDTO;
import com.sakura.cloud.demo1.service.UserService;
import com.sakura.cloud.demo1.vo.UserVO;
import com.sakura.common.db.mp.CommonPage;
import com.sakura.common.ratelimit.guava.RateLimiter;
import com.sakura.common.result.CommonResult;
import com.sakura.common.web.auth.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通过本示例，熟悉编写的一些规范：
 *      1、返回给前端的统一结构
 *      2、处理异常
 *      3、swagger的常规使用
 *      建议：项目组自行选择是否使用lombok
 *
 * @auther YangFan
 * @Date 2020/11/30 10:57
 */

@RestController
@RequestMapping("/sakura")
@Api(value = "用户管理接口", tags = {"用户管理接口"})
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RateLimiter(value = 1.0, timeout = 300)
    @ApiOperation("查询数据")
    @GetMapping(value = "/users")
    public CommonResult<CommonPage<UserVO>> queryUsers(UserDTO userDTO) {
        log.info("当前登录用户信息："+  UserContext.get());
        log.info("开始查询了==1111111");
        IPage<UserVO> users = userService.queryUsers(userDTO);
        return CommonResult.success(CommonPage.restPage(users));
    }

    @RateLimiter(value = 1.0, timeout = 300)
    @ApiOperation("使用Payload提交批量添加用户")
    @PostMapping(value = "/users")
    public CommonResult<String> saveUserWithPayload(@RequestBody List<UserDTO> userList) {
        userService.saveUsers(userList);
        return CommonResult.success("成功批量添加用户!");
    }

    @RateLimiter(value = 1.0, timeout = 300)
    @ApiOperation("表单接收参数添加用户")
    @PostMapping(value = "/formdata/users")
    public CommonResult<String> saveUserWithFormData(UserDTO userDTO) {
        userService.saveUser(userDTO);
        return CommonResult.success("成功添加用户!");
    }
}
