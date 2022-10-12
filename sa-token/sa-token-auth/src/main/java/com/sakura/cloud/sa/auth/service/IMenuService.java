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
    boolean create(Menu menu);

    /**
     * 修改后台菜单
     */
    boolean update(Long id, Menu menu);

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
    boolean updateHidden(Long id, Integer hidden);
}
