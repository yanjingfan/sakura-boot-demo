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
 * @since 2023-03-20
 */
@Getter
@Setter
@TableName("lqb_user")
@ApiModel(value = "User对象", description = "后台用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "lqb_id", type = IdType.AUTO)
    private Long lqbId;

    @ApiModelProperty("用户名")
    private String lqbUsername;

    @ApiModelProperty("密码")
    private String lqbPasswd;

    @ApiModelProperty("盐")
    private String lqbSalt;

    @ApiModelProperty("昵称")
    private String lqbNickName;

    @ApiModelProperty("家庭地址")
    private String lqbAddress;

    @ApiModelProperty("头像")
    private String lqbIcon;

    @ApiModelProperty("邮箱")
    private String lqbEmail;

    @ApiModelProperty("最后登录时间")
    private LocalDateTime lqbLoginTime;

    @ApiModelProperty("手机号")
    private String lqbMobile;

    @ApiModelProperty("第二个手机号")
    private String lqbMobileTwo;

    @ApiModelProperty("座机号码")
    private String lqbTelephone;

    @ApiModelProperty("帐号启用状态：1->启用；0->禁用")
    private Integer lqbUserStatus;

    @ApiModelProperty("用户来源：0->自填；1->管理员添加；2->微信；3：第三方")
    private Integer lqbSource;

    @ApiModelProperty("是否是管理员：1->是；0->否")
    private Integer lqbAdmin;

    @ApiModelProperty("排序字段")
    private Long lqbOrderNum;

    @ApiModelProperty("平台id")
    private Integer lqbPlatformId;

    @ApiModelProperty("创建人id")
    private Long lqbCreateUserId;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime lqbCreateTime;

    @ApiModelProperty("修改人id")
    private Long lqbUpdateUserId;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime lqbUpdateTime;

    @ApiModelProperty("是否被删除：1->已删除；0->未删除")
    @TableLogic
    private Integer lqbDeleted;


}
