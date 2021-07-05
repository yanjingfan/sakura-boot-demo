package com.sakura.cloud.demo1.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @auther YangFan
 * @Date 2021/1/11 16:40
 */
@Data
@ToString
public class UserExcel implements Serializable {

    @Excel(name = "用户账号", height = 20, width = 30, isImportField = "true_st")
    private String userId;//用户账号

    @Excel(name = "用户名称", height = 20, width = 30, isImportField = "true_st")
    private String username;//用户名称

    @Excel(name = "学生性别", replace = { "男_1", "女_2" }, suffix = "生", isImportField = "true_st")
    private String sex;

    @Excel(name = "入校时间", format = "yyyy-MM-dd", isImportField = "true_st", width = 20)
    private LocalDate createTime;//创建时间
}
