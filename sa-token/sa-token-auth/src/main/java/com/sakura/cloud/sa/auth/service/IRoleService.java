package com.sakura.cloud.sa.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.cloud.sa.auth.entity.Menu;
import com.sakura.cloud.sa.auth.entity.Resource;
import com.sakura.cloud.sa.auth.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 服务类
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
public interface IRoleService extends IService<Role> {
    /**
     * 添加角色
     */
    void create(Role role);

    /**
     * 批量删除角色
     */
    void delete(List<Long> ids);

    /**
     * 分页获取角色列表
     */
    Page<Role> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 根据管理员ID获取对应菜单
     */
    List<Menu> getMenuList(Long userId);

    /**
     * 获取角色相关菜单
     */
    List<Menu> listMenu(Long roleId);

    /**
     * 获取角色相关资源
     */
    List<Resource> listResource(Long roleId);

    /**
     * 给角色分配菜单
     */
    @Transactional
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     * 给角色分配资源
     */
    @Transactional
    int allocResource(Long roleId, List<Long> resourceIds);
}
