package com.sakura.cloud.demo1.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 项目表
 * </p>
 *
 * @author yangfan
 * @since 2022-08-04
 */
@Data
@TableName("project_manage")
@ApiModel(value = "ProjectManage对象", description = "项目表")
public class ProjectManage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("项目分组id，关联project_group分组表")
    private Long groupId;

    @ApiModelProperty("项目类型id，关联project_sys_dictonary字典表主键id")
    private Long typeId;

    @ApiModelProperty("项目名称")
    private String projectName;

    @ApiModelProperty("项目编号")
    private String projectCode;

    @ApiModelProperty("合同签订时间")
    private LocalDateTime contractSignTime;

    @ApiModelProperty("合同规定终验时间")
    private LocalDateTime contractRuleFinalAcceptTime;

    @ApiModelProperty("开工时间")
    private LocalDateTime startTime;

    @ApiModelProperty("开工模式id，关联project_sys_dictonary字典表主键id")
    private Long startWorkModelId;

    @ApiModelProperty("合同编号")
    private String contractCode;

    @ApiModelProperty("客户名称")
    private String customerName;

    @ApiModelProperty("渠道来源id，关联project_sys_dictonary字典表主键id")
    private Long channelSourceId;

    @ApiModelProperty("销售部门")
    private String saleDepartment;

    @ApiModelProperty("产品线id，关联project_sys_dictonary字典表主键id")
    private Long productLineId;

    @ApiModelProperty("合同额")
    private String contractAmount;

    @ApiModelProperty("合同额-产品")
    private String contractAmountProd;

    @ApiModelProperty("合同额-集成")
    private String contractAmountGather;

    @ApiModelProperty("合同额-服务")
    private String contractAmountService;

    @ApiModelProperty("项目背景")
    private String projectBackgroud;

    @ApiModelProperty("项目范围（内容描述）")
    private String projectDesc;

    @ApiModelProperty("项目需求：（标书/合同）")
    private String projectNeeds;

    @ApiModelProperty("项目状态")
    private Integer projectStatus;

    @ApiModelProperty("项目风险")
    private String projectRisk;

    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("是否删除（1：已删除，0：未删除）")
    @TableLogic
    private Integer deleted;
}
