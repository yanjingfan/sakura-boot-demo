package com.sakura.cloud.demo1.service;

import com.sakura.cloud.demo1.dto.UserDTO;
import com.sakura.cloud.demo1.vo.CategoryAppJsonVO;

/**
 *
 * 远程接口调用示例
 *
 * @auther YangFan
 * @Date 2021/1/8 14:00
 */
public interface RemoteService {

    CategoryAppJsonVO queryApps(Long page, Long pageSize, String keyword);

    CategoryAppJsonVO getRemoteDate(Long page, Long pageSize);

    void saveUser(UserDTO userDTO);

}
