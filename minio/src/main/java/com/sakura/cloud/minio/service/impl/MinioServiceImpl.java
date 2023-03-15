package com.sakura.cloud.minio.service.impl;

import com.sakura.cloud.minio.dto.MinioDto;
import com.sakura.cloud.minio.service.MinioService;
import com.sakura.cloud.minio.support.FileType;
import com.sakura.cloud.minio.vo.MinioFileVo;
import com.sakura.common.exception.YErrorException;
import com.sakura.common.minio.config.BucketNameConfig;
import com.sakura.common.minio.template.MinioTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MinioServiceImpl implements MinioService {

    @Autowired
    private MinioTemplate minioTemplate;

    @Override
    public List<MinioFileVo> uploadList(MinioDto minioDto) {

        //根据上传的文件类型，选择对应的文件夹，这里默认使用FILE
        String type = FileType.FILE.getType();
        Integer dateFile = minioDto.getDateFile();
        String bucketName = minioDto.getBucketName();
        String perfixName = minioDto.getPerfixName();
        List<MultipartFile> upfileList = minioDto.getUpfileList();

        List<MinioFileVo> resultList = new ArrayList<>();
        StringBuffer sbFile = new StringBuffer();
        if (StringUtils.isNotBlank(perfixName)) {
            sbFile.append(perfixName).append(BucketNameConfig.FILE_SPLIT_PATH);
        }
        if (dateFile == 1) {
            // 创建时间文件夹
            sbFile.append(BucketNameConfig.getYear());
            sbFile.append(BucketNameConfig.FILE_SPLIT_PATH);
            sbFile.append(BucketNameConfig.getMonthAndDay());
            sbFile.append(BucketNameConfig.FILE_SPLIT_PATH);
        }
        try {
            for (MultipartFile file : upfileList) {
                String fileName = file.getOriginalFilename();
                sbFile.append(System.currentTimeMillis());
                sbFile.append("_");
                fileName = sbFile.toString() + fileName;
                MinioFileVo minioFile = new MinioFileVo();
                minioTemplate.createBucket(bucketName);
                minioTemplate.putObject(bucketName, fileName, file.getInputStream());
                String objectURL = minioTemplate.getObjectURL(bucketName, fileName);
                String url = StringUtils.substringBefore(objectURL, "?");
                minioFile.setBucketName(bucketName);
                minioFile.setFileName(fileName);
                minioFile.setFileUrl(url);
                resultList.add(minioFile);
            }
        } catch (Exception e) {
            log.error("文件上传异常", e);
            throw new YErrorException("文件上传异常");
        }
        return resultList;
    }
}
