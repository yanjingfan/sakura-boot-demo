package com.sakura.cloud.sa.auth.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 人员部门表
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
@Getter
@Setter
@TableName("s_department")
@ApiModel(value = "Department对象", description = "人员部门表")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("部门主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("部门code")
    private String deptCode;

    @ApiModelProperty("上级部门ID")
    private Long parentId;

    @ApiModelProperty("部门路径")
    private String parentPath;

    @ApiModelProperty("说明")
    private String remark;

    @ApiModelProperty("创建人ID")
    private Long createUserId;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("操作人ID")
    private Long updateUserId;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("是否被删除：1->已删除；0->未删除")
    @TableLogic
    private Boolean deleted;


}
