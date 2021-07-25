package com.sakura.cloud.demo1.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

	//传到前台的时间格式
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private LocalDateTime createTime;//创建时间
}
