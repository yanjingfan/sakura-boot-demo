package com.sakura.cloud.demo1.vo;

import java.sql.Date;

/**
 * 权限平台-用户表
 * @author admin
 * @since 2020-10-21 10:49
 **/
public class UserVO {
	private String userId;//用户账号

	private String username;//用户名称

	private Long tenantId;//租户序号，租户主键

	private String sex;

	private Date createTime;//创建时间

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}
