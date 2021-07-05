package com.sakura.cloud.demo1.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * 权限平台-用户表
 * @author admin
 * @since 2020-10-21 10:49
 **/
@Data
public class UserVO {
	private String userId;//用户账号

	private String username;//用户名称

	private Long tenantId;//租户序号，租户主键

	private String sex;

	private LocalDate createTime;//创建时间
}
