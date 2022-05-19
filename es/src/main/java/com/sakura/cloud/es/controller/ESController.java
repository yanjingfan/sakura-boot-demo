package com.sakura.cloud.es.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakura.common.es.ESTemplate;
import com.sakura.common.es.item.FilterInfo;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther yangfan
 * @date 2022/1/26
 * @describle
 */
@RestController
@RequestMapping("/sakura")
@Api(value = "ES通用查询", tags = {"ES通用查询"})
public class ESController {

    @Autowired
    private ESTemplate esTemplate;

    @ApiOperation(value = "ES通用查询", notes = "ES通用查询")
    @PostMapping(value = "/es/getObjectString")
    public CommonResult<Object> userInfoExport(@RequestBody FilterInfo filterInfo) throws JsonProcessingException {
        String objectString = esTemplate.getObjectString(filterInfo);
        ObjectMapper mapper = new ObjectMapper();
        return CommonResult.success(mapper.readValue(objectString, Object.class));
    }

    @ApiOperation(value = "ES通用查询", notes = "ES通用查询")
    @PostMapping(value = "/es/postObjectString")
    public CommonResult<Object> postObjectString(@RequestBody FilterInfo filterInfo) throws JsonProcessingException {
        String objectString = esTemplate.postObjectString(filterInfo);
        ObjectMapper mapper = new ObjectMapper();
        return CommonResult.success(mapper.readValue(objectString, Object.class));
    }
}
