package com.sakura.cloud.minio.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class MinioDto {

    //上传的文件对象集合
    @NotNull(message = "请上传文件!")
    @ApiParam(name = "upfileList", value = "上传的文件")
    List<MultipartFile> upfileList;

    //所属的存储桶（第一级目录）
//    @NotNull(message = "bucketName不能为空!")
//    @ApiParam(name = "bucketName", value = "文件桶名称")
//    String bucketName;

    @ApiParam(name = "perfixName", value = "文件前缀名称")
    String perfixName;

    @ApiParam(name = "dateFile", value = "默认创建时间文件夹：1是，其它否")
    Integer dateFile = 1;
}
