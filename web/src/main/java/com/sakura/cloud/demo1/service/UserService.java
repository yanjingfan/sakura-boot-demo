package com.sakura.cloud.demo1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sakura.cloud.demo1.dto.UserDTO;
import com.sakura.cloud.demo1.entity.UserEntity;
import com.sakura.cloud.demo1.vo.UserVO;

/**
 * @auther YangFan
 * @Date 2020/12/24 16:54
 */
public interface UserService  extends IService<UserEntity> {

    IPage<UserVO> queryUsers(Long page, Long pageSize);

    void saveUser(UserDTO userDTO);

}
