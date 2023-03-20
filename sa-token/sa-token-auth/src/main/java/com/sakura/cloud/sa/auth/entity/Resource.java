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
 * 资源表
 * </p>
 *
 * @author yangfan
 * @since 2023-03-20
 */
@Getter
@Setter
@TableName("lqb_resource")
@ApiModel(value = "Resource对象", description = "资源表")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("资源主键ID")
    @TableId(value = "lqb_id", type = IdType.AUTO)
    private Long lqbId;

    @ApiModelProperty("菜单主键ID")
    private Long lqbMenuId;

    @ApiModelProperty("资源名称")
    private String lqbResourceName;

    @ApiModelProperty("资源编号")
    private String lqbResourceCode;

    @ApiModelProperty("资源URL")
    private String lqbUrl;

    @ApiModelProperty("资源描述")
    private String lqbDescription;

    @ApiModelProperty("资源分类ID")
    private Long lqbCategoryId;

    @ApiModelProperty("创建人ID")
    private String lqbCreateUserId;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime lqbCreateTime;

    @ApiModelProperty("操作人ID")
    private String lqbUpdateUserId;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime lqbUpdateTime;

    @ApiModelProperty("是否被删除：1->已删除；0->未删除")
    @TableLogic
    private Integer lqbDeleted;


}
