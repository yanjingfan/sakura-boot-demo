package com.sakura.cloud.demo1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.cloud.demo1.dto.UserDTO;
import com.sakura.cloud.demo1.entity.UserEntity;
import com.sakura.cloud.demo1.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    IPage<UserVO> queryUsers(@Param("page") Page<UserVO> page, @Param("user") UserDTO user);

}
