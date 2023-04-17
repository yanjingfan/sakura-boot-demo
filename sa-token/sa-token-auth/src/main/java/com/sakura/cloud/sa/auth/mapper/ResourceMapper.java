package com.sakura.cloud.sa.auth.mapper;

import com.sakura.cloud.sa.auth.entity.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
public interface ResourceMapper extends BaseMapper<Resource> {
    /**
     * 获取用户所有可访问资源
     */
    List<Resource> getResourceListByUserId(@Param("userId") Long userId);

    /**
     * 根据角色ID获取资源
     */
    List<Resource> getResourceListByRoleId(@Param("roleId") Long roleId);

    List<Resource> getResourceList();
}
