package com.sakura.cloud.sa.auth.service.impl;

import com.sakura.cloud.sa.auth.entity.Department;
import com.sakura.cloud.sa.auth.mapper.DepartmentMapper;
import com.sakura.cloud.sa.auth.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 人员部门表 服务实现类
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
