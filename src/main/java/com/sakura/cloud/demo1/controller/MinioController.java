package com.sakura.cloud.demo1.controller;

import com.sakura.cloud.demo1.vo.MinioFileVo;
import com.sakura.common.minio.config.BucketNameConfig;
import com.sakura.common.minio.template.MinioTemplate;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    MinioTemplate minioTemplate;

    /**
     * 多上传文件
     *
     * @param upfileList 上传的文件对象集合
     * @param bucketName 所属的存储桶（第一级目录）
     * @return
     */
    @ApiOperation(value = "多文件上传")
    @PostMapping("fileList")
    public CommonResult<Object> uploadList(@ApiParam(name = "upfileList", value = "文件集合参数名称") @RequestParam("upfileList") List<MultipartFile> upfileList,
                                           @ApiParam(name = "bucketName", value = "文件桶名称") String bucketName,
                                           @ApiParam(name = "perfixName", value = "文件前缀名称") String perfixName,
                                           @ApiParam(name = "dateFile", value = "是否需要创建时间文件夹：1是，其它否") Integer dateFile) {
        if (StringUtils.isBlank(bucketName)) {
            return CommonResult.failed("bucketName不能为空!");
        }
        List<MinioFileVo> resultList = new ArrayList<>();
        StringBuffer sbFile = new StringBuffer();
        if (StringUtils.isNotBlank(perfixName)) {
            sbFile.append(perfixName).append(BucketNameConfig.FILE_SPLIT_PATH);
        }
        if (dateFile != null && dateFile == 1) {
            // 创建时间文件夹
            sbFile.append(BucketNameConfig.getYear());
            sbFile.append(BucketNameConfig.FILE_SPLIT_PATH);
            sbFile.append(BucketNameConfig.getMonthAndDay());
            sbFile.append(BucketNameConfig.FILE_SPLIT_PATH);
        }
        for (MultipartFile file : upfileList) {
            String fileName = file.getOriginalFilename();
            fileName = sbFile.toString() + fileName;
            try {
                MinioFileVo minioFile = new MinioFileVo();
                minioTemplate.createBucket(bucketName);
                minioTemplate.putObject(bucketName, fileName, file.getInputStream());
                String objectURL = minioTemplate.getObjectURL(bucketName, fileName);
                minioFile.setBucketName(bucketName);
                minioFile.setFileName(fileName);
                minioFile.setFileUrl(objectURL);
                resultList.add(minioFile);
            } catch (Exception e) {
                log.error("文件上传异常", e);
                return CommonResult.failed();
            }
        }
        return CommonResult.success(resultList);
    }
}
