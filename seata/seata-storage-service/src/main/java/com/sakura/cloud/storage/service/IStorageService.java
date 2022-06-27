package com.sakura.cloud.storage.service;

import com.sakura.cloud.storage.entity.Storage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangfan
 * @since 2022-06-27
 */
public interface IStorageService extends IService<Storage> {
    /**
     * 扣减库存
     */
    void decrease(Long productId, Integer count);
}
