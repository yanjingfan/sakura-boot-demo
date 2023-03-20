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
 * 后台角色资源关系表
 * </p>
 *
 * @author yangfan
 * @since 2023-03-20
 */
@Getter
@Setter
@TableName("lqb_role_resource_middle")
@ApiModel(value = "RoleResourceMiddle对象", description = "后台角色资源关系表")
public class RoleResourceMiddle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "lqb_id", type = IdType.AUTO)
    private Long lqbId;

    @ApiModelProperty("角色ID")
    private Long lqbRoleId;

    @ApiModelProperty("资源ID")
    private Long lqbResourceId;


}
