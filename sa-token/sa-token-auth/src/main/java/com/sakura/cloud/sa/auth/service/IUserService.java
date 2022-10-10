package com.sakura.cloud.sa.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.sakura.cloud.sa.auth.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
public interface IUserService extends IService<User> {
    SaTokenInfo pcLogin(String username, String password);

    void loginOut(String loginDevice);
}
