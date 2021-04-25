package com.sakura.cloud.demo1.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sakura.cache.service.RedisService;
import com.sakura.cloud.demo1.service.UserService;
import com.sakura.cloud.demo1.vo.UserVO;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yangfan
 * @date 2021/4/25 14:02
 * @description Redis测试Controller
 * @modified By
 */
@Api(tags = "RedisController", description = "Redis测试")
@Controller
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @ApiOperation("测试简单缓存")
    @RequestMapping(value = "/simpleTest", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<UserVO> simpleTest() {
        IPage<UserVO> users = userService.queryUsers(1l, 5l);
        List<UserVO> userList = users.getRecords();
        UserVO user = null;
        if (!CollectionUtils.isEmpty(userList)) {
            UserVO userVO = userList.get(0);
            String key = "redis:simple:" + userVO.getUserId();
            redisService.set(key, userVO);
            user = (UserVO) redisService.get(key);
        }
        return CommonResult.success(user);
    }

    @ApiOperation("测试Hash结构的缓存")
    @RequestMapping(value = "/hashTest", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<UserVO> hashTest() {
        IPage<UserVO> users = userService.queryUsers(1l, 5l);
        List<UserVO> userList = users.getRecords();

        UserVO cacheUser = null;
        if (!CollectionUtils.isEmpty(userList)) {
            UserVO user = userList.get(0);
            String key = "redis:hash:" + user.getUserId();
            Map<String, Object> value = BeanUtil.beanToMap(user);
            redisService.hSetAll(key, value);
            Map<Object, Object> cacheValue = redisService.hGetAll(key);
            cacheUser = BeanUtil.toBeanIgnoreError(cacheValue, UserVO.class);
        }
        return CommonResult.success(cacheUser);
    }

    @ApiOperation("测试Set结构的缓存")
    @RequestMapping(value = "/setTest", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Set<Object>> setTest() {
        IPage<UserVO> users = userService.queryUsers(1l, 5l);
        List<UserVO> userList = users.getRecords();
        Set<Object> cachedUserList = null;
        if (!CollectionUtils.isEmpty(userList)) {
            String key = "redis:set:all";
            redisService.sAdd(key, (Object[]) ArrayUtil.toArray(userList, UserVO.class));
            redisService.sRemove(key, userList.get(0));
            cachedUserList = redisService.sMembers(key);
        }
        return CommonResult.success(cachedUserList);
    }

    @ApiOperation("测试List结构的缓存")
    @RequestMapping(value = "/listTest", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Object>> listTest() {
        IPage<UserVO> users = userService.queryUsers(1l, 5l);
        List<UserVO> userList = users.getRecords();

        List<Object> cachedUserList = null;
        if (!CollectionUtils.isEmpty(userList)) {
            String key = "redis:list:all";
            redisService.lPushAll(key, (Object[]) ArrayUtil.toArray(userList, UserVO.class));
            redisService.lRemove(key, 1, userList.get(0));
            cachedUserList = redisService.lRange(key, 0, 3);
        }
        return CommonResult.success(cachedUserList);
    }
}
