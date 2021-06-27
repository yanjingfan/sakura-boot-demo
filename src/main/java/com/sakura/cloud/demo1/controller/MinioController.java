package com.sakura.cloud.demo1.controller;

import com.sakura.cloud.demo1.dto.MinioDto;
import com.sakura.cloud.demo1.service.MinioService;
import com.sakura.cloud.demo1.vo.MinioFileVo;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @auther yangfan
 * @date 2021/6/21
 * @describle minio文件服务器相关接口
 */
@Slf4j
@RestController
@RequestMapping("/minio")
@Api(value = "minio上传文件", tags = {"minio上传文件"})
public class MinioController {

    @Autowired
    private MinioService minioService;

    /**
     * 多上传文件
     */
    @ApiOperation(value = "多文件上传")
    @PostMapping("fileList")
    public CommonResult<List<MinioFileVo>> uploadList(@Valid MinioDto minioDto) {
        List<MinioFileVo> resultList = minioService.uploadList(minioDto);
        return CommonResult.success(resultList);
    }
}
