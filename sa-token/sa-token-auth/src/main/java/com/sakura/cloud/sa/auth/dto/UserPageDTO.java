package com.sakura.cloud.sa.auth.dto;

import lombok.Data;

/**
 * @auther yangfan
 * @date 2022/10/12
 * @describle 用户分页查询dto
 */
@Data
public class UserPageDTO {

    /**
     * 搜索值
     */
    private String searchKey;

    /**
     * 分页数
     */
    private Long pageNum = 1L;

    /**
     * 分页大小
     */
    private Long pageSize = 10L;
}
