package com.sakura.cloud.sa.auth.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 后台用户角色表
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
@Getter
@Setter
@TableName("s_role")
@ApiModel(value = "Role对象", description = "后台用户角色表")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("名称")
    private String roleName;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("后台用户数量")
    private Long userCount;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("启用状态：0->禁用；1->启用")
    private Integer roleStatus;

    private Integer orderNum;


}
