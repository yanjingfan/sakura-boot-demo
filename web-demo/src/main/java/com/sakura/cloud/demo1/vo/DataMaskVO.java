package com.sakura.cloud.demo1.vo;

import com.sakura.common.web.datamask.DataMask;
import com.sakura.common.web.datamask.DataMaskEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 权限平台-用户表
 * @author admin
 * @since 2020-10-21 10:49
 **/
@AllArgsConstructor
@Data
public class DataMaskVO {

	@DataMask(function = DataMaskEnum.USERNAME)
	private String username;//用户名称

	@DataMask(function = DataMaskEnum.PHONE)
	private String phone;

	@DataMask(function = DataMaskEnum.EMAIL)
	private String emial;

	@DataMask(function = DataMaskEnum.ADDRESS)
	private String address;
}
