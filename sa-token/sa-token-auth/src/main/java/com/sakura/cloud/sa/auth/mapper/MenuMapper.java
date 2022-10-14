package com.sakura.cloud.sa.auth.mapper;

import com.sakura.cloud.sa.auth.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据后台用户ID获取菜单
     */
    List<Menu> getMenuList(@Param("userId") Long userId);
    /**
     * 根据角色ID获取菜单
     */
    List<Menu> getMenuListByRoleId(@Param("roleId") Long roleId);
}
