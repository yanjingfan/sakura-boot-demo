package com.sakura.cloud.demo1.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @auther yangfan
 * @date 2021/6/17
 * @describle
 *
 * sakura-boot脚手架已经对时间参数做了处理，以下几个注解了解即可
 *  @DateTimeFormat 用于请求体非json格式的请求
 *  @JsonFormat/@JSONField 用于请求体为json格式的请求，将包含此注解的对象返回给前端时,也会沿用此注解的格式 (返回类型为json格式时)
 */

@Data
public class TimeDTO {

    /**
     * 使用Jackson
     */
    @ApiModelProperty("localDateTime，格式为yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    @ApiModelProperty("date，格式为yyyy-MM-dd HH:mm:ss或者yyyy-MM-dd")
    private Date date;

    /**
     * 使用fastjson
     */
    @ApiModelProperty("localDate，格式为yyyy-MM-dd")
    private LocalDate localDate;

//    @ApiModelProperty("localTime，格式为HH:mm:ss")
//    private LocalTime localTime;

}
