package com.sakura.cloud.demo1.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @auther yangfan
 * @date 2021/6/17
 * @describle 这样就不用把时间字段定义为字符串，然后再转换为时间字段了
 */

@Data
public class TimeDTO {

    @ApiModelProperty("开始时间，格式为yyyy-MM-dd HH:mm:ss")
    /**
     * 传到前台的时间格式，使用Jackson
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    //传到后台的时间格式
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 传到前台的时间格式，使用fastjson
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    //传到后台的时间格式
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

}
