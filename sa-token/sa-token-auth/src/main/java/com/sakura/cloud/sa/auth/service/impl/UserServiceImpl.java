package com.sakura.cloud.sa.auth.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakura.cloud.sa.auth.constant.LoginDeviceConstant;
import com.sakura.cloud.sa.auth.dto.MenuTree;
import com.sakura.cloud.sa.auth.dto.UpdateUserPassWordDTO;
import com.sakura.cloud.sa.auth.dto.UserPageDTO;
import com.sakura.cloud.sa.auth.entity.Department;
import com.sakura.cloud.sa.auth.entity.Role;
import com.sakura.cloud.sa.auth.entity.User;
import com.sakura.cloud.sa.auth.entity.UserRoleMiddle;
import com.sakura.cloud.sa.auth.mapper.RoleMapper;
import com.sakura.cloud.sa.auth.mapper.UserMapper;
import com.sakura.cloud.sa.auth.service.IUserRoleMiddleService;
import com.sakura.cloud.sa.auth.service.IUserService;
import com.sakura.cloud.sa.auth.vo.UserVO;
import com.sakura.common.domian.UserDTO;
import com.sakura.common.exception.YWarmingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 用户管理业务类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserRoleMiddleService userRoleMiddleService;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User loadUserByUsername(String username) {
        QueryWrapper<User> wq = new QueryWrapper<>();
        wq.lambda().eq(User::getLqbUsername, username);
        User user = this.getOne(wq);
        return user;
    }

    @Override
    public SaTokenInfo pcLogin(UserDTO dto) {
        String username = dto.getLqbUsername();
        String password = dto.getLqbPasswd();
        User user = loadUserByUsername(username);
        if (user == null) {
            throw new YWarmingException("用户名或者密码错误");
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

        StringBuilder sb = new StringBuilder(user.getLqbSalt());
        sb.append(password);
        String passwdSha256 = SaSecureUtil.sha256(sb.toString());
        if (!passwdSha256.equals(user.getLqbPasswd())) {
            throw new YWarmingException("用户名或者密码错误");
        }
        // 密码校验成功后登录，一行代码实现登录
        StpUtil.login(user.getLqbId(), LoginDeviceConstant.PC);
        // 将用户信息存储到Session中
//        StpUtil.getSession().set("userInfo",userDTO);
        // 获取当前登录用户Token信息
        return StpUtil.getTokenInfo();
    }

    @Override
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
        wrapper.lambda().eq(User::getLqbUsername, user.getLqbUsername());
        User existUser = this.getOne(wrapper);
        if (existUser != null) {
            throw new YWarmingException("该用户名已被占用");
        }
        //密码加密
        String salt = UUID.randomUUID().toString().replace("-", "");
        user.setLqbSalt(salt);
        StringBuilder sb = new StringBuilder(salt);
        sb.append(user.getLqbPasswd());
        String passwdSha256 = SaSecureUtil.sha256(sb.toString());
        user.setLqbPasswd(passwdSha256);
        this.save(user);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public IPage<UserVO> userPage(UserPageDTO dto) {
        Page<UserVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<UserVO> users = this.baseMapper.userPage(page, dto);
        return users;
    }

    @Override
    public UserVO getUser(Long id) {
        User user = this.getById(id);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public void updateUser(Long id, UserDTO dto) {
        User user = this.getById(id);
        BeanUtils.copyProperties(dto, user);
        //这里不修改用户名密码
        user.setLqbPasswd(null);
        this.updateById(user);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        User user = new User();
        user.setLqbId(id);
        user.setLqbUserStatus(status);
        this.updateById(user);
    }

    @Override
    public void updateRole(Long userId, List<Long> roleIds) {
        //先删除原来的关系
        QueryWrapper<UserRoleMiddle> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserRoleMiddle::getLqbUserId, userId);
        userRoleMiddleService.remove(wrapper);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<UserRoleMiddle> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                UserRoleMiddle roleMiddle = new UserRoleMiddle();
                roleMiddle.setLqbUserId(userId);
                roleMiddle.setLqbRoleId(roleId);
                list.add(roleMiddle);
            }
            userRoleMiddleService.saveBatch(list);
        }
    }

    @Override
    public List<Role> getRoleList(Long userId) {
        return roleMapper.getRoleListByUserId(userId);
    }

    @Override
    public void updatePassword(UpdateUserPassWordDTO dto) {
        String newPassword = dto.getNewPassword();
        String oldPassword = dto.getOldPassword();
        String username = dto.getUsername();

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getLqbUsername, username);
        User existUser = this.getOne(wrapper);
        if (existUser == null) {
            throw new YWarmingException("找不到该用户");
        }

        String salt = existUser.getLqbSalt();
        StringBuilder sb = new StringBuilder(salt);
        sb.append(oldPassword);
        String oldPasswdSha256 = SaSecureUtil.sha256(sb.toString());
        if (!oldPasswdSha256.equals(existUser.getLqbPasswd())) {
            throw new YWarmingException("旧密码错误");
        }

        StringBuilder sBuilder = new StringBuilder(salt);
        sBuilder.append(newPassword);
        String newPasswdSha256 = SaSecureUtil.sha256(sBuilder.toString());
        existUser.setLqbPasswd(newPasswdSha256);
        this.updateById(existUser);
    }

    @Override
    public void deleteUser(Long id) {
        this.removeById(id);
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
