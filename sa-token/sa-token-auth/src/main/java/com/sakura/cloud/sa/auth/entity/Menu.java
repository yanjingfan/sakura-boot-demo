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
 * 菜单表
 * </p>
 *
 * @author yangfan
 * @since 2023-03-20
 */
@Getter
@Setter
@TableName("lqb_menu")
@ApiModel(value = "Menu对象", description = "菜单表")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单主键ID")
    @TableId(value = "lqb_id", type = IdType.AUTO)
    private Long lqbId;

    @ApiModelProperty("菜单名称")
    private String lqbMenuName;

    @ApiModelProperty("菜单编号")
    private String lqbMenuCode;

    @ApiModelProperty("菜单级数")
    private Integer lqbMenuLevel;

    @ApiModelProperty("排序")
    private Integer lqbOrderNum;

    @ApiModelProperty("上级菜单ID")
    private Long lqbParentId;

    @ApiModelProperty("菜单路径")
    private String lqbParentPath;

    @ApiModelProperty("说明")
    private String lqbRemark;

    @ApiModelProperty("前端名称")
    private String lqbWebName;

    @ApiModelProperty("菜单访问路径")
    private String lqbUrl;

    @ApiModelProperty("图标路径")
    private String lqbIcon;

    @ApiModelProperty("前端隐藏：0->不隐藏；1->隐藏")
    private Integer lqbHidden;

    @ApiModelProperty("创建人ID")
    private Long lqbCreateUserId;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime lqbCreateTime;

    @ApiModelProperty("操作人ID")
    private Long lqbUpdateUserId;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime lqbUpdateTime;

    @ApiModelProperty("是否被删除：1->已删除；0->未删除")
    @TableLogic
    private Integer lqbDeleted;


}
