package com.sakura.cloud.sa.auth.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import org.springframework.stereotype.Service;

/**
 * 用户管理业务类
 */
@Service
public interface UserService {

    SaTokenInfo pcLogin(String username, String password);

    void loginOut(String loginDevice);

}
