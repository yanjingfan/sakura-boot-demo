package com.sakura.cloud.demo1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakura.cloud.demo1.entity.ProjectManage;
import com.sakura.cloud.demo1.mapper.ProjectManageMapper;
import com.sakura.cloud.demo1.service.IProjectManageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author yangfan
 * @since 2022-08-04
 */
@Service
public class ProjectManageServiceImpl extends ServiceImpl<ProjectManageMapper, ProjectManage> implements IProjectManageService {

}
