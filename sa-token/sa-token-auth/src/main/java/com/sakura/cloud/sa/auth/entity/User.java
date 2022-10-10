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
 * 后台用户表
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
@Getter
@Setter
@TableName("s_user")
@ApiModel(value = "User对象", description = "后台用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String passwd;

    @ApiModelProperty("盐")
    private String salt;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("家庭地址")
    private String address;

    @ApiModelProperty("头像")
    private String icon;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("最后登录时间")
    private LocalDateTime loginTime;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("第二个手机号")
    private String mobileTwo;

    @ApiModelProperty("座机号码")
    private String phone;

    @ApiModelProperty("帐号启用状态：1->启用；0->禁用")
    private Boolean userStatus;

    @ApiModelProperty("用户来源：0->自填；1->管理员添加；2->微信；3：第三方")
    private Boolean source;

    @ApiModelProperty("是否是管理员：1->是；0->否")
    private Boolean admin;

    @ApiModelProperty("排序字段")
    private Long sort;

    @ApiModelProperty("平台id")
    private Integer platformId;

    @ApiModelProperty("创建人id")
    private Long createUserId;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("修改人id")
    private Long updateUserId;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("是否被删除：1->已删除；0->未删除")
    @TableLogic
    private Boolean deleted;


}