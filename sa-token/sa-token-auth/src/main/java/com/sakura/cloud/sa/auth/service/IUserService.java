package com.sakura.cloud.sa.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.cloud.sa.auth.dto.UpdateUserPassWordDTO;
import com.sakura.cloud.sa.auth.dto.UserPageDTO;
import com.sakura.cloud.sa.auth.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sakura.cloud.sa.auth.vo.UserVO;
import com.sakura.common.domian.UserDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
public interface IUserService extends IService<User> {

    /**
     * 登录
     * @param dto
     * @return
     */
    SaTokenInfo pcLogin(UserDTO dto);

    /**
     * 登出
     * @param loginDevice
     */
    void loginOut(String loginDevice);

    /**
     * 用户注册
     */
    UserVO register(UserDTO dto);

    /**
     * 根据用户名或昵称分页查询用户
     */
    IPage<UserVO> userPage(UserPageDTO dto);

    /**
     * 根据用户id查找用户
     */
    UserVO getUser(Long id);

    /**
     * 修改指定用户信息
     */
    void updateUser(Long id, UserDTO admin);

    /**
     * 修改用户状态
     */
    void updateStatus(Long id, Integer status);

    /**
     * 修改用户密码
     */
    void updatePassword(UpdateUserPassWordDTO dto);

    /**
     * 删除指定用户
     */
    void deleteUser(Long id);

    /**
     * 获取用户信息
     */
    User loadUserByUsername(String username);
}
