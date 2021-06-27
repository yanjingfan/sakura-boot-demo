package com.sakura.cloud.demo1.vo;

import lombok.Data;

/**
 * @auther yangfan
 * @date 2021/6/22
 * @describle
 */

@Data
public class MinioFileVo {

    private String fileName;

    private String bucketName;

    private String fileUrl;

}
