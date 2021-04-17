package com.sakura.cloud.demo1.vo;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author yangfan
 * @date 2021/4/17 14:28
 * @description
 * @modified By
 */

@Data
public class CategoryAppJsonVO<T> {

    /**
     * 状态码
     */
    private long code;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 数据封装
     */
    private T data;

}
