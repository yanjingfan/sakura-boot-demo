package com.sakura.cloud.sa.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sakura.cloud.sa.auth.entity.Resource;
import com.sakura.cloud.sa.auth.entity.Role;
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
