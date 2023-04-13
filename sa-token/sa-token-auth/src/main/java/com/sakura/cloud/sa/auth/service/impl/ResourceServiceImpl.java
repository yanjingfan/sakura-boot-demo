package com.sakura.cloud.sa.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakura.cloud.sa.auth.entity.Menu;
import com.sakura.cloud.sa.auth.entity.Resource;
import com.sakura.cloud.sa.auth.mapper.ResourceMapper;
import com.sakura.cloud.sa.auth.service.IMenuService;
import com.sakura.cloud.sa.auth.service.IResourceService;
import com.sakura.common.exception.YErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IMenuService iMenuService;

    @Override
    public List<Resource> listResourceByRoleId(Long roleId) {
        return this.baseMapper.getResourceListByRoleId(roleId);
    }

    @Override
    public void create(Resource resource) {
        this.save(resource);
    }

    @Override
    public void update(Long id, Resource resource) {
        Resource resource1 = this.getById(id);
        if (resource1 == null) {
            throw new YErrorException("未找到对应资源");
        }

        Menu menu = iMenuService.getById(resource1.getLqbMenuId());
        if (menu == null) {
            throw new YErrorException("未找到对应菜单");
        }
        resource.setLqbId(id);
        this.updateById(resource);
    }

    @Override
    public void delete(Long id) {
        this.removeById(id);
    }

    @Override
    public Page<Resource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
        Page<Resource> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Resource> lambda = new LambdaQueryWrapper<>();
        if (categoryId != null) {
            lambda.eq(Resource::getLqbCategoryId, categoryId);
        }
        if (StrUtil.isNotEmpty(nameKeyword)) {
            lambda.like(Resource::getLqbResourceName, nameKeyword);
        }
        if (StrUtil.isNotEmpty(urlKeyword)) {
            lambda.like(Resource::getLqbUrl, urlKeyword);
        }
        return this.page(page, lambda);
    }
}
