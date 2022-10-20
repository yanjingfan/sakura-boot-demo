package com.sakura.cloud.sa.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakura.cloud.sa.auth.entity.ResourceCategory;

import java.util.List;

/**
 * <p>
 * 资源分类表 服务类
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
public interface IResourceCategoryService extends IService<ResourceCategory> {
    /**
     * 获取所有资源分类
     */
    List<ResourceCategory> listAll();

    /**
     * 创建资源分类
     */
    void create(ResourceCategory resourceCategory);
}
