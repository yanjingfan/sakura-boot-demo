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
 * 后台角色菜单关系表
 * </p>
 *
 * @author yangfan
 * @since 2023-03-20
 */
@Getter
@Setter
@TableName("lqb_role_menu_middle")
@ApiModel(value = "RoleMenuMiddle对象", description = "后台角色菜单关系表")
public class RoleMenuMiddle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "lqb_id", type = IdType.AUTO)
    private Long lqbId;

    @ApiModelProperty("角色ID")
    private Long lqbRoleId;

    @ApiModelProperty("菜单ID")
    private Long lqbMenuId;


}
