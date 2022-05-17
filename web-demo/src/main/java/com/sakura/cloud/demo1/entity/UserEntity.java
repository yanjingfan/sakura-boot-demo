package com.sakura.cloud.demo1.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限平台-用户表
 *
 * @author admin
 * @since 2020-10-21 10:49
 **/
@Data
@TableName("t_user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id")
    private String userId;//用户账号

    @TableField("username")
    private String username;//用户名称

    @TableField("passwd")
    private String passwd;//用户名称

    @TableField("tenant_id")
    private Long tenantId;

    @TableField("sex")
    private String sex;

    @TableField("create_time")
    private LocalDateTime createTime;//创建时间

}
