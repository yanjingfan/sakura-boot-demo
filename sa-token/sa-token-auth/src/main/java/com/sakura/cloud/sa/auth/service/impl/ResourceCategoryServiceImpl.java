package com.sakura.cloud.sa.auth.service.impl;

import com.sakura.cloud.sa.auth.entity.ResourceCategory;
import com.sakura.cloud.sa.auth.mapper.ResourceCategoryMapper;
import com.sakura.cloud.sa.auth.service.IResourceCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资源分类表 服务实现类
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
@Service
public class ResourceCategoryServiceImpl extends ServiceImpl<ResourceCategoryMapper, ResourceCategory> implements IResourceCategoryService {

}
