package com.sakura.cloud.minio.service.impl;

import com.sakura.cloud.minio.dto.MinioDto;
import com.sakura.cloud.minio.service.MinioService;
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

        String bucketName = minioDto.getBucketName();
        Integer dateFile = minioDto.getDateFile();
        String perfixName = minioDto.getPerfixName();
        List<MultipartFile> upfileList = minioDto.getUpfileList();

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
                throw new YErrorException("文件上传异常");
            }
        }
        return resultList;
    }
}
