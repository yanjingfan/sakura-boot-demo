package com.sakura.cloud.storage.service.impl;

import com.sakura.cloud.storage.entity.Storage;
import com.sakura.cloud.storage.mapper.StorageMapper;
import com.sakura.cloud.storage.service.IStorageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangfan
 * @since 2022-06-27
 */
@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements IStorageService {

}
