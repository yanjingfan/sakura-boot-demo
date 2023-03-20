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
 * 部门菜单关联表
 * </p>
 *
 * @author yangfan
 * @since 2023-03-20
 */
@Getter
@Setter
@TableName("lqb_department_menu")
@ApiModel(value = "DepartmentMenu对象", description = "部门菜单关联表")
public class DepartmentMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "lqb_id", type = IdType.AUTO)
    private Long lqbId;

    @ApiModelProperty("菜单主键ID")
    private Long lqbMenuId;

    @ApiModelProperty("部门主键ID")
    private Long lqbDeptId;


}
