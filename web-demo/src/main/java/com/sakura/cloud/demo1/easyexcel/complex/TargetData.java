package com.sakura.cloud.demo1.easyexcel.complex;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @auther yangfan
 * @date 2022/3/23
 * @describle
 */
@Data
@EqualsAndHashCode
@ApiModel("项目立项计划-导入")
public class TargetData {

    @ExcelProperty("阶段")
    private String firstTarget;

    @ExcelProperty("预算类型")
    private String firstTargetType;

    @ExcelProperty("数字标题")
    private Double doubleData;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}
