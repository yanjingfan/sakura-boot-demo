package com.sakura.cloud.demo1.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.sql.Date;

/**
 * @auther YangFan
 * @Date 2021/1/11 16:40
 */
public class UserEntity implements Serializable {

    @Excel(name = "用户账号", height = 20, width = 30, isImportField = "true_st")
    private String userId;//用户账号

    @Excel(name = "用户名称", height = 20, width = 30, isImportField = "true_st")
    private String username;//用户名称

    @Excel(name = "学生性别", replace = { "男_1", "女_2" }, suffix = "生", isImportField = "true_st")
    private String sex;

    @Excel(name = "入校时间", format = "yyyy-MM-dd", isImportField = "true_st", width = 20)
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
