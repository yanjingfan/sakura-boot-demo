package com.sakura.cloud.demo1.service;

import com.sakura.cloud.demo1.dto.UserDTO;
import com.sakura.cloud.demo1.vo.CategoryAppJsonVO;

import java.util.List;

/**
 *
 * 远程接口调用示例
 *
 * @auther YangFan
 * @Date 2021/1/8 14:00
 */
public interface RemoteService {

    CategoryAppJsonVO queryUsers(Long page, Long pageSize);

    CategoryAppJsonVO getRemoteDate(Long page, Long pageSize);

    void saveUser(List<UserDTO> userDTO);

    void saveUserWithFormData(UserDTO userDTO);

}
