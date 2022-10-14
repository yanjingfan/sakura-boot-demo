package com.sakura.cloud.sa.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakura.cloud.sa.auth.entity.Role;
import com.sakura.cloud.sa.auth.entity.RoleMenuMiddle;
import com.sakura.cloud.sa.auth.entity.RoleResourceMiddle;
import com.sakura.cloud.sa.auth.mapper.RoleMapper;
import com.sakura.cloud.sa.auth.service.IRoleMenuMiddleService;
import com.sakura.cloud.sa.auth.service.IRoleResourceMiddleService;
import com.sakura.cloud.sa.auth.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private IRoleMenuMiddleService roleMenuRelationService;

    @Autowired
    private IRoleResourceMiddleService roleResourceRelationService;

    @Override
    public void create(Role role) {
        role.setUserCount(0L);
        role.setSort(0);
        this.save(role);
    }

    @Override
    public void delete(List<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public Page<Role> list(String keyword, Integer pageSize, Integer pageNum) {
        Page<Role> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<Role> lambda = wrapper.lambda();
        if (StrUtil.isNotEmpty(keyword)) {
            lambda.like(Role::getRoleName, keyword);
        }
        return page(page, wrapper);
    }

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        //先删除原有关系
        QueryWrapper<RoleMenuMiddle> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RoleMenuMiddle::getRoleId,roleId);
        roleMenuRelationService.remove(wrapper);
        //批量插入新关系
        List<RoleMenuMiddle> relationList = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenuMiddle relation = new RoleMenuMiddle();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            relationList.add(relation);
        }
        roleMenuRelationService.saveBatch(relationList);
        return menuIds.size();
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        //先删除原有关系
        QueryWrapper<RoleResourceMiddle> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RoleResourceMiddle::getRoleId,roleId);
        roleResourceRelationService.remove(wrapper);
        //批量插入新关系
        List<RoleResourceMiddle> relationList = new ArrayList<>();
        for (Long resourceId : resourceIds) {
            RoleResourceMiddle relation = new RoleResourceMiddle();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            relationList.add(relation);
        }
        roleResourceRelationService.saveBatch(relationList);
        return resourceIds.size();
    }
}
