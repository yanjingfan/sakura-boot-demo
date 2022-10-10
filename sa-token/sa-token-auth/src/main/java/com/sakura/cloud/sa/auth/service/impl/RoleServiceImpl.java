package com.sakura.cloud.sa.auth.service.impl;

import com.sakura.cloud.sa.auth.entity.Role;
import com.sakura.cloud.sa.auth.mapper.RoleMapper;
import com.sakura.cloud.sa.auth.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户角色表 服务实现类
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
