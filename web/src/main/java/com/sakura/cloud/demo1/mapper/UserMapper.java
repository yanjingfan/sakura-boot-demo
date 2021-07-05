package com.sakura.cloud.demo1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.cloud.demo1.entity.UserPO;
import com.sakura.cloud.demo1.excel.UserExcel;
import com.sakura.cloud.demo1.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface UserMapper extends BaseMapper<UserPO> {

    IPage<UserVO> queryUsers(@Param("page") Page<UserVO> page);

}
