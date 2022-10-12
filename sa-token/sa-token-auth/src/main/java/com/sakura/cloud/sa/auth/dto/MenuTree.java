package com.sakura.cloud.sa.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单树类
 * created by xiaokun on 2022/9/2.
 */
@Data
public class MenuTree {

    @ApiModelProperty("菜单主键ID")
    private Long id;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("菜单编号")
    private String menuCode;

    @ApiModelProperty("菜单级数")
    private Integer menuLevel;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("上级菜单ID")
    private Long parentId;

    @ApiModelProperty("菜单路径")
    private String parentPath;

    @ApiModelProperty("说明")
    private String remark;

    @ApiModelProperty("前端名称")
    private String webName;

    @ApiModelProperty("菜单访问路径")
    private String url;

    @ApiModelProperty("图标路径")
    private String icon;

    @ApiModelProperty("是否显示：0->不显示；1->显示")
    private Integer show;

    /**
     * 子菜单集合
     */
    private List<MenuTree> children = new ArrayList();
}
