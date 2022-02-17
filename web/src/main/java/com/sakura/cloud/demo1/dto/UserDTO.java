package com.sakura.cloud.demo1.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @auther YangFan
 * @Date 2020/12/28 14:19
 */
@Data
@ApiModel(value = "用户dto")
public class UserDTO {

    @ApiModelProperty("用户账号")
    private String userId;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("用户密码")
    private String passwd;//用户名称

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("租户账号")
    private Long tenantId;

    @ApiModelProperty("分页数")
    private Long pageNum;

    @ApiModelProperty("每页大小")
    private Long pageSize;
}
