package com.sakura.cloud.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @auther YangFan
 * @Date 2020/12/28 14:19
 */
@ApiModel(value = "用户dto")
public class UserDTO {

    @ApiModelProperty("用户账号")
    private String userId;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("租户账号")
    private Long tenantId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
