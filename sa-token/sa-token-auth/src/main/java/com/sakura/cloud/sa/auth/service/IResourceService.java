package com.sakura.cloud.sa.auth.service;

import com.sakura.cloud.sa.auth.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
