package com.sakura.cloud.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import com.sakura.cloud.vo.UserVO;
import com.sakura.common.cache.RedisUtils;
import com.sakura.common.cache.ratelimit.RateLimiter;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

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
    private RedisUtils redisUtils;

    /**
     * @RateLimiter(value = 5)：一分钟只能请求五次
     * @return
     */
    @RateLimiter(value = 5)
    @ApiOperation("测试简单缓存")
    @RequestMapping(value = "/simpleTest", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<UserVO> simpleTest() {
        List<UserVO> userList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            UserVO user = new UserVO();
            user.setUserId(i+"");
            user.setCreateTime(new Date());
            user.setSex(1+"");
            user.setTenantId((long) i);
            user.setUsername("test"+i);
            userList.add(user);
        }

        UserVO user = null;
        if (!CollectionUtils.isEmpty(userList)) {
            UserVO userVO = userList.get(0);
            String key = "redis:simple:" + userVO.getUserId();
            redisUtils.set(key, userVO);
            user = (UserVO) redisUtils.get(key);
        }
        return CommonResult.success(user);
    }

    @ApiOperation("测试Hash结构的缓存")
    @RequestMapping(value = "/hashTest", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<UserVO> hashTest() {
        List<UserVO> userList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            UserVO user = new UserVO();
            user.setUserId(i+"");
            user.setCreateTime(new Date());
            user.setSex(1+"");
            user.setTenantId((long) i);
            user.setUsername("test"+i);
            userList.add(user);
        }

        UserVO cacheUser = null;
        if (!CollectionUtils.isEmpty(userList)) {
            UserVO user = userList.get(0);
            String key = "redis:hash:" + user.getUserId();
            Map<String, Object> value = BeanUtil.beanToMap(user);
            redisUtils.hSetAll(key, value);
            Map<Object, Object> cacheValue = redisUtils.hGetAll(key);
            cacheUser = BeanUtil.toBeanIgnoreError(cacheValue, UserVO.class);
        }
        return CommonResult.success(cacheUser);
    }

    @ApiOperation("测试Set结构的缓存")
    @RequestMapping(value = "/setTest", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Set<Object>> setTest() {
        List<UserVO> userList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            UserVO user = new UserVO();
            user.setUserId(i+"");
            user.setCreateTime(new Date());
            user.setSex(1+"");
            user.setTenantId((long) i);
            user.setUsername("test"+i);
            userList.add(user);
        }
        Set<Object> cachedUserList = null;
        if (!CollectionUtils.isEmpty(userList)) {
            String key = "redis:set:all";
            redisUtils.sAdd(key, (Object[]) ArrayUtil.toArray(userList, UserVO.class));
            redisUtils.sRemove(key, userList.get(0));
            cachedUserList = redisUtils.sMembers(key);
        }
        return CommonResult.success(cachedUserList);
    }

    @ApiOperation("测试List结构的缓存")
    @RequestMapping(value = "/listTest", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Object>> listTest() {
        List<UserVO> userList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            UserVO user = new UserVO();
            user.setUserId(i+"");
            user.setCreateTime(new Date());
            user.setSex(1+"");
            user.setTenantId((long) i);
            user.setUsername("test"+i);
            userList.add(user);
        }

        List<Object> cachedUserList = null;
        if (!CollectionUtils.isEmpty(userList)) {
            String key = "redis:list:all";
            redisUtils.lPushAll(key, (Object[]) ArrayUtil.toArray(userList, UserVO.class));
            redisUtils.lRemove(key, 1, userList.get(0));
            cachedUserList = redisUtils.lRange(key, 0, 3);
        }
        return CommonResult.success(cachedUserList);
    }
}
