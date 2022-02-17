package com.sakura.cloud.demo1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sakura.cloud.demo1.dto.UserDTO;
import com.sakura.cloud.demo1.entity.UserEntity;
import com.sakura.cloud.demo1.vo.UserVO;

import java.util.List;

/**
 * @auther YangFan
 * @Date 2020/12/24 16:54
 */
public interface UserService  extends IService<UserEntity> {

    IPage<UserVO> queryUsers(UserDTO userDTO);

    void saveUser(UserDTO userDTO);

    void saveUsers(List<UserDTO> userList);

}
