package com.sakura.cloud.sa.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakura.cloud.sa.auth.dto.MenuTree;
import com.sakura.cloud.sa.auth.entity.Menu;
import com.sakura.cloud.sa.auth.mapper.MenuMapper;
import com.sakura.cloud.sa.auth.service.IMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public void create(Menu menu) {
        updateLevel(menu);
        this.save(menu);
    }

    /**
     * 修改菜单层级
     */
    private void updateLevel(Menu menu) {
        if (menu.getParentId() == 0) {
            //没有父菜单时为一级菜单
            menu.setMenuLevel(0);
        } else {
            //有父菜单时选择根据父菜单level设置
            Menu parentMenu = getById(menu.getParentId());
            if (parentMenu != null) {
                menu.setMenuLevel(parentMenu.getMenuLevel() + 1);
            } else {
                menu.setMenuLevel(0);
            }
        }
    }

    @Override
    public void update(Long id, Menu menu) {
        menu.setId(id);
        updateLevel(menu);
        this.updateById(menu);
    }

    @Override
    public List<Menu> getMenuList(Long userId) {
        return this.baseMapper.getMenuList(userId);
    }

    @Override
    public List<Menu> listMenuByRoleId(Long roleId) {
        return this.baseMapper.getMenuListByRoleId(roleId);
    }

    @Override
    public Page<Menu> list(Long parentId, Integer pageSize, Integer pageNum) {
        Page<Menu> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Menu::getParentId, parentId)
                .orderByDesc(Menu::getOrderNum);
        return page(page, wrapper);
    }

    @Override
    public List<MenuTree> treeList() {
        List<Menu> menuList = this.list();
        List<MenuTree> result = menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuTree(menu, menuList)).collect(Collectors.toList());
        return result;
    }

    /**
     * 将Menu转化为MenuTree并设置children属性
     */
    private MenuTree covertMenuTree(Menu menu, List<Menu> menuList) {
        MenuTree node = new MenuTree();
        BeanUtils.copyProperties(menu, node);
        List<MenuTree> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuTree(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    @Override
    public void updateHidden(Long id, Integer hidden) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setHidden(hidden);
        this.updateById(menu);
    }
}
