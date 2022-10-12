package com.sakura.cloud.sa.auth.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.cloud.sa.auth.dto.UserPageDTO;
import com.sakura.cloud.sa.auth.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.cloud.sa.auth.vo.UserVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
public interface UserMapper extends BaseMapper<User> {
    IPage<UserVO> userPage(@Param("page") Page<UserVO> page, @Param("dto") UserPageDTO dto);
}
