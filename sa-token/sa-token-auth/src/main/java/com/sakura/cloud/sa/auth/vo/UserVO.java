package com.sakura.cloud.sa.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @auther yangfan
 * @date 2022/10/11
 * @describle
 */
@Data
@ApiModel(value = "用户信息VO", description = "用户信息VO")
public class UserVO {

    private Long id;

    @ApiModelProperty("用户名")
    private String username;

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
    private String telephone;

    @ApiModelProperty("帐号启用状态：1->启用；0->禁用")
    private Integer userStatus;

    @ApiModelProperty("用户来源：0->自填；1->管理员添加；2->微信；3：第三方")
    private Integer source;

    @ApiModelProperty("是否是管理员：1->是；0->否")
    private Integer admin;

    @ApiModelProperty("排序字段")
    private Long sort;

    @ApiModelProperty("平台id")
    private Integer platformId;
}
