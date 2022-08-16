package com.sakura.cloud.demo1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.cloud.demo1.entity.ProjectManage;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 项目表 Mapper 接口
 * </p>
 *
 * @author yangfan
 * @since 2022-08-04
 */
@Mapper
public interface ProjectManageMapper extends BaseMapper<ProjectManage> {

}
