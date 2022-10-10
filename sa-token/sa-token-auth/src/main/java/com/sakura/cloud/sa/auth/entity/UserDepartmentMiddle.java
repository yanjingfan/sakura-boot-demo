package com.sakura.cloud.sa.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 人员部门关联表
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
@Getter
@Setter
@TableName("s_user_department_middle")
@ApiModel(value = "UserDepartmentMiddle对象", description = "人员部门关联表")
public class UserDepartmentMiddle implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("人员主键ID")
    private Long userId;

    @ApiModelProperty("部门主键ID")
    private Long deptId;


}
