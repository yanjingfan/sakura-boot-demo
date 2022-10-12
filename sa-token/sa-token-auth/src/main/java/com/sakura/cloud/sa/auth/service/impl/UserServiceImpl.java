package com.sakura.cloud.sa.auth.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakura.cloud.sa.auth.constant.LoginDeviceConstant;
import com.sakura.cloud.sa.auth.entity.Department;
import com.sakura.cloud.sa.auth.entity.User;
import com.sakura.cloud.sa.auth.mapper.UserMapper;
import com.sakura.cloud.sa.auth.service.IUserService;
import com.sakura.cloud.sa.auth.vo.UserVO;
import com.sakura.common.domian.MenuTree;
import com.sakura.common.domian.UserDTO;
import com.sakura.common.exception.YWarmingException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 用户管理业务类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    public User loadUserByUsername(String username) {
        QueryWrapper<User> wq = new QueryWrapper<>();
        wq.lambda().eq(User::getUsername, username);
        User user = this.getOne(wq);
        return user;
    }

    public SaTokenInfo pcLogin(UserDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPasswd();
        User user = loadUserByUsername(username);
        if (user == null) {
            return null;
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        //获取菜单、资源
        this.getPremissionInfo(userVO);
        //获取所属部门
        List<Department> departments = new ArrayList<>();
        Department department = new Department();
        departments.add(department);
        userVO.setDepartments(departments);

        StringBuilder sb = new StringBuilder(user.getSalt());
        sb.append(password);
        String passwdSha256 = SaSecureUtil.sha256(sb.toString());
        if (!passwdSha256.equals(user.getPasswd())) {
            return null;
        }
        // 密码校验成功后登录，一行代码实现登录
        StpUtil.login(user.getId(), LoginDeviceConstant.PC);
        // 将用户信息存储到Session中
//        StpUtil.getSession().set("userInfo",userDTO);
        // 获取当前登录用户Token信息
        return StpUtil.getTokenInfo();
    }

    public void loginOut(String loginDevice) {
        if (LoginDeviceConstant.PC.equals(loginDevice.toUpperCase())) {
            StpUtil.logout(StpUtil.getLoginIdAsLong(), LoginDeviceConstant.PC);
        } else if (LoginDeviceConstant.WX.equals(loginDevice.toUpperCase())) {
            StpUtil.logout(StpUtil.getLoginIdAsLong(), LoginDeviceConstant.WX);
        }
    }

    @Override
    public UserVO register(UserDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        //查询是否有相同用户名的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getUsername, user.getUsername());
        User existUser = this.getOne(wrapper);
        if (existUser != null) {
            throw new YWarmingException("该用户名已被占用");
        }
        //密码加密
        String salt = UUID.randomUUID().toString().replace("-", "");
        user.setSalt(salt);
        StringBuilder sb = new StringBuilder(salt);
        sb.append(user.getPasswd());
        String passwdSha256 = SaSecureUtil.sha256(sb.toString());
        user.setPasswd(passwdSha256);
        this.save(user);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    private UserVO getPremissionInfo(UserVO user) {
        //菜单
        List<MenuTree> menuList = new ArrayList<>();
        //资源
        List<String> resourceList = new ArrayList<>();
        //过滤
        List<String> filterList = new ArrayList<>();
        user.setMenuList(menuList);
        user.setResourceList(resourceList);
        user.setFilterList(filterList);
        return user;
    }
}
