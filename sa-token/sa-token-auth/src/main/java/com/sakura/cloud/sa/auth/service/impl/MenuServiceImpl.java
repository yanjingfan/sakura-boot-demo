package com.sakura.cloud.sa.auth.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakura.cloud.sa.auth.dto.MenuTree;
import com.sakura.cloud.sa.auth.entity.Menu;
import com.sakura.cloud.sa.auth.mapper.MenuMapper;
import com.sakura.cloud.sa.auth.service.IMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public boolean create(Menu menu) {
        return false;
    }

    @Override
    public boolean update(Long id, Menu menu) {
        return false;
    }

    @Override
    public Page<Menu> list(Long parentId, Integer pageSize, Integer pageNum) {
        return null;
    }

    @Override
    public List<MenuTree> treeList() {
        return null;
    }

    @Override
    public boolean updateHidden(Long id, Integer hidden) {
        return false;
    }
}
