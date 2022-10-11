package com.sakura.cloud.sa.auth.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakura.cloud.sa.auth.constant.LoginDeviceConstant;
import com.sakura.cloud.sa.auth.entity.User;
import com.sakura.cloud.sa.auth.mapper.UserMapper;
import com.sakura.cloud.sa.auth.service.IUserService;
import com.sakura.common.domian.MenuTree;
import com.sakura.common.domian.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理业务类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private List<UserDTO> userList;

    @PostConstruct
    public void initData() {
        userList = new ArrayList<>();
        userList.add(UserDTO.builder()
                .id(1L)
                .username("admin")
                .passwd(SaSecureUtil.md5("123456"))
                .resourceList(CollUtil.toList("api:user:info","api:test:hello"))
                .build());
        userList.add(UserDTO.builder()
                .id(2L)
                .username("macro")
                .passwd(SaSecureUtil.md5("123456"))
                .resourceList(CollUtil.toList("api:user:info"))
                .build());
    }

    public UserDTO loadUserByUsername(String username) {
        List<UserDTO> findUserList = userList.stream().filter(item -> item.getUsername().equals(username)).collect(Collectors.toList());
        if (CollUtil.isEmpty(findUserList)) {
            return null;
        }
        return findUserList.get(0);
    }

    public SaTokenInfo pcLogin(UserDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPasswd();
        UserDTO userDTO = loadUserByUsername(username);
        if (userDTO == null) {
            return null;
        }
        //获取菜单、资源
        this.getPremissionInfo(userDTO);
        String deptId = "";
        String deptName = "";
        userDTO.setDepartmentId(deptId);
        userDTO.setDepartmentName(deptName);
        if (!SaSecureUtil.md5(password).equals(userDTO.getPasswd())) {
            return null;
        }
        // 密码校验成功后登录，一行代码实现登录
        StpUtil.login(userDTO.getId(), LoginDeviceConstant.PC);
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
    public void register(UserDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);

    }

    private UserDTO getPremissionInfo(UserDTO userDTO) {
        //菜单
        List<MenuTree> menuList = new ArrayList<>();
        //资源
        List<String> resourceList = new ArrayList<>();
        //过滤
        List<String> filterList = new ArrayList<>();
        userDTO.setMenuList(menuList);
        userDTO.setResourceList(resourceList);
        userDTO.setFilterList(filterList);
        return userDTO;
    }
}
