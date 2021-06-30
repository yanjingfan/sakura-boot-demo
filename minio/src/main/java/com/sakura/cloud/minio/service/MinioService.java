package com.sakura.cloud.minio.service;

import com.sakura.cloud.minio.dto.MinioDto;
import com.sakura.cloud.minio.vo.MinioFileVo;

import java.util.List;

public interface MinioService {

    List<MinioFileVo> uploadList(MinioDto minioDto);
}
