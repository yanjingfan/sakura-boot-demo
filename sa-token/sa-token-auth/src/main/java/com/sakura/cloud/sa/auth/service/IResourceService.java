package com.sakura.cloud.sa.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sakura.cloud.sa.auth.entity.Resource;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
public interface IResourceService extends IService<Resource> {
    /**
     * 获取角色相关资源
     */
    List<Resource> listResourceByRoleId(Long roleId);

    /**
     * 添加资源
     */
    void create(Resource resource);

    /**
     * 修改资源
     */
    void update(Long id, Resource resource);

    /**
     * 删除资源
     */
    void delete(Long id);

    /**
     * 分页查询资源
     */
    Page<Resource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);
}
