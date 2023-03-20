package com.sakura.cloud.sa.auth.vo;

import com.sakura.cloud.sa.auth.dto.MenuTree;
import com.sakura.cloud.sa.auth.entity.Department;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @auther yangfan
 * @date 2022/10/11
 * @describle
 */
@Data
@ApiModel(value = "用户信息VO", description = "用户信息VO")
public class UserVO {

    private Long lqbId;

    @ApiModelProperty("用户名")
    private String lqbUsername;

    @ApiModelProperty("昵称")
    private String lqbNickName;

    @ApiModelProperty("家庭地址")
    private String lqbAddress;

    @ApiModelProperty("头像")
    private String lqbIcon;

    @ApiModelProperty("邮箱")
    private String lqbEmail;

    @ApiModelProperty("最后登录时间")
    private LocalDateTime loginTime;

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

    @ApiModelProperty("部门")
    private List<Department> departments;

    @ApiModelProperty("菜单")
    private List<MenuTree> menuList;

    /**
     * 资源，控制菜单按钮的显示
     */
    @ApiModelProperty("资源")
    private List<String> resourceList;

    /**
     * 资源过滤，控制查询是否添加权限控制
     */
    @ApiModelProperty("资源过滤")
    private List<String> filterList;
}
