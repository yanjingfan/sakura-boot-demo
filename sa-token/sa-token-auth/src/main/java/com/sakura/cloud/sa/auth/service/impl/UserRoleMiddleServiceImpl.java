package com.sakura.cloud.sa.auth.service.impl;

import com.sakura.cloud.sa.auth.entity.UserRoleMiddle;
import com.sakura.cloud.sa.auth.mapper.UserRoleMiddleMapper;
import com.sakura.cloud.sa.auth.service.IUserRoleMiddleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户和角色关系表 服务实现类
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
@Service
public class UserRoleMiddleServiceImpl extends ServiceImpl<UserRoleMiddleMapper, UserRoleMiddle> implements IUserRoleMiddleService {

}
