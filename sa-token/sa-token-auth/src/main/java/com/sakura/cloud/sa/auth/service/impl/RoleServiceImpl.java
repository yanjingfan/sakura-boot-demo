package com.sakura.cloud.sa.auth.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.cloud.sa.auth.entity.Menu;
import com.sakura.cloud.sa.auth.entity.Resource;
import com.sakura.cloud.sa.auth.entity.Role;
import com.sakura.cloud.sa.auth.mapper.RoleMapper;
import com.sakura.cloud.sa.auth.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public void create(Role role) {

    }

    @Override
    public void delete(List<Long> ids) {

    }

    @Override
    public Page<Role> list(String keyword, Integer pageSize, Integer pageNum) {
        return null;
    }

    @Override
    public List<Menu> getMenuList(Long userId) {
        return null;
    }

    @Override
    public List<Menu> listMenu(Long roleId) {
        return null;
    }

    @Override
    public List<Resource> listResource(Long roleId) {
        return null;
    }

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        return 0;
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        return 0;
    }
}
