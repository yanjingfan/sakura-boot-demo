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
 * 资源分类表
 * </p>
 *
 * @author yangfan
 * @since 2023-03-20
 */
@Getter
@Setter
@TableName("lqb_resource_category")
@ApiModel(value = "ResourceCategory对象", description = "资源分类表")
public class ResourceCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "lqb_id", type = IdType.AUTO)
    private Long lqbId;

    @ApiModelProperty("分类名称")
    private String lqbCategoryName;

    @ApiModelProperty("排序")
    private Integer lqbOrderNum;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime lqbCreateTime;


}
