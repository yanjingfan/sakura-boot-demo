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
 * @describle
 *  @DateTimeFormat 用于请求体非json格式的请求
 *  @JsonFormat/@JSONField 用于请求体为json格式的请求，将包含此注解的对象返回给前端时,也会沿用此注解的格式 (返回类型为json格式时)
 */

@Data
public class TimeDTO {



    @ApiModelProperty("开始时间，格式为yyyy-MM-dd HH:mm:ss")
    /**
     * 使用Jackson
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 使用fastjson
     */
    @ApiModelProperty("结束时间，格式为yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

}
