package com.sakura.cloud.sa.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sakura.cloud.sa.auth.dto.MenuTree;
import com.sakura.cloud.sa.auth.entity.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 创建后台菜单
     */
    void create(Menu menu);

    /**
     * 修改后台菜单
     */
    void update(Long id, Menu menu);

    /**
     * 根据管理员ID获取对应菜单
     */
    List<Menu> getMenuListByUserId(Long userId);

    /**
     * 根据角色ID获取菜单
     */
    List<Menu> listMenuByRoleId(Long roleId);

    /**
     * 分页查询后台菜单
     */
    Page<Menu> list(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 树形结构返回所有菜单列表
     */
    List<MenuTree> treeList();

    /**
     * 修改菜单显示状态
     */
    void updateHidden(Long id, Integer hidden);

    int getMaxMenuId();

    List<Menu> getMenuList();

    List<MenuTree> treeList(List<Menu> menuList);
}
