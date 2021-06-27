package com.sakura.cloud.demo1.service;

import com.sakura.cloud.demo1.dto.MinioDto;
import com.sakura.cloud.demo1.vo.MinioFileVo;

import java.util.List;

public interface MinioService {

    List<MinioFileVo> uploadList(MinioDto minioDto);
}
