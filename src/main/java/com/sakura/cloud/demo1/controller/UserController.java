package com.sakura.cloud.demo1.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sakura.cloud.demo1.dto.UserDTO;
import com.sakura.cloud.demo1.service.UserService;
import com.sakura.cloud.demo1.vo.UserVO;
import com.sakura.common.exception.CloudException;
import com.sakura.common.result.CommonResult;
import com.sakura.common.web.auth.UserContext;
import com.sakura.db.mp.CommonPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", dataType = "long", required = true, value = "当前页码", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "long", required = true, value = "每页条数", defaultValue = "10")
    })
    @GetMapping(value = "/users")
    public CommonResult queryUsers(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize) {
        try {
           
            log.info("当前登录用户信息："+  UserContext.get());
            log.info("开始查询了=======================");
            IPage<UserVO> users = userService.queryUsers(page, pageSize);
            return CommonResult.success(CommonPage.restPage(users));
        } catch(CloudException e) {
            log.error(e.getMessage(), e);//已知异常，输出说明
            return CommonResult.failed(e.getMessage());
        } catch (Exception e) {//未知异常
            log.error(e.getMessage(), e);
            return CommonResult.failed();
        }
    }

    @ApiOperation("添加用户")
    @PostMapping(value = "/users")
    public CommonResult saveUser(@RequestBody UserDTO userDTO) {
        try {
            userService.saveUser(userDTO);
            return CommonResult.success("成功添加用户!");
        } catch(CloudException e) {
            log.error(e.getMessage(), e);//已知异常，输出说明
            return CommonResult.failed(e.getMessage());
        } catch (Exception e) {//未知异常
            log.error(e.getMessage(), e);
            return CommonResult.failed();
        }
    }
}
