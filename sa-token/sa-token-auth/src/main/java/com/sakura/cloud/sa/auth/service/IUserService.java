package com.sakura.cloud.sa.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.sakura.cloud.sa.auth.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sakura.cloud.sa.auth.vo.UserVO;
import com.sakura.common.domian.UserDTO;

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
}
