package com.sakura.cloud.demo1.controller;

import com.sakura.cloud.demo1.dto.TimeDTO;
import com.sakura.common.config.date.DateHandlerConfig;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @auther yangfan
 * @date 2022/4/14
 * @describle
 *
 * sakura-boot组件中common模块，处理了LocalDateTime、LocalDate、Date、LocalTime类型的参数，无
 * 需再添加@DateTimeFormat和@JsonFormat/@JSONField，就可以传递时间参数
 *
 * 时间格式统一处理：{@link DateHandlerConfig}
 *
 */
@RestController
@RequestMapping("/sakura")
@Api(value = "接受时间参数", tags = {"接受时间参数"})
public class TimeController {

    @ApiOperation("url上传递时间参数")
    @GetMapping(value = "/url/time")
    public CommonResult<TimeDTO> queryTest1(@RequestParam(required = false) LocalDateTime localDateTime,
                                            @RequestParam(required = false) LocalDate localDate,
                                            @RequestParam(required = false) LocalTime localTime,
                                            @RequestParam(required = false) Date date) {
        TimeDTO dto = new TimeDTO();
        dto.setDate(date);
        dto.setLocalDate(localDate);
//        dto.setLocalTime(localTime);
        dto.setLocalDateTime(localDateTime);
        return CommonResult.success(dto);
    }

    @ApiOperation("表单传递时间参数")
    @PostMapping(value = "/form/time")
    public CommonResult<TimeDTO> queryTest2(TimeDTO dto) {
        return CommonResult.success(dto);
    }

    @ApiOperation("json传递时间参数")
    @PostMapping(value = "/json/time")
    public CommonResult<TimeDTO> queryTest3(@RequestBody TimeDTO dto) {
        return CommonResult.success(dto);
    }
}
