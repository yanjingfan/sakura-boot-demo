package com.sakura.cloud.sa.auth.service.impl;

import com.sakura.cloud.sa.auth.entity.Resource;
import com.sakura.cloud.sa.auth.mapper.ResourceMapper;
import com.sakura.cloud.sa.auth.service.IResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Override
    public List<Resource> listResourceByRoleId(Long roleId) {
        return this.baseMapper.getResourceListByRoleId(roleId);
    }
}
