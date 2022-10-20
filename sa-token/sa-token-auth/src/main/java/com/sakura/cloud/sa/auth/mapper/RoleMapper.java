package com.sakura.cloud.sa.auth.mapper;

import com.sakura.cloud.sa.auth.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 Mapper 接口
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 获取用户所有角色
     */
    List<Role> getRoleListByUserId(@Param("userId") Long userId);
}
