package com.sakura.cloud.demo1.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakura.cloud.demo1.dto.UserDTO;
import com.sakura.cloud.demo1.entity.UserEntity;
import com.sakura.cloud.demo1.mapper.UserMapper;
import com.sakura.cloud.demo1.service.UserService;
import com.sakura.cloud.demo1.vo.UserVO;
import com.sakura.common.exception.CloudException;
import com.sakura.common.exception.YErrorException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @auther YangFan
 * @Date 2020/12/24 16:54
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 查询用户信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public IPage<UserVO> queryUsers(Long pageNum, Long pageSize) {

        try {
            Page<UserVO> page = new Page<>(pageNum, pageSize);

            //单表自己编写sql查询
            IPage<UserVO> users = userMapper.queryUsers(page);

            //使用MybatisPlus单表查询分页，查询zhxh=-1的用户
            //因为传的参数是PO对象，返回的分页对象里也是PO对象，会破坏分层架构，推荐返回给前端的是VO对象
//            Page<UserPO> pagePO = new Page<>(pageNum, pageSize);
//            QueryWrapper<UserPO> wrapper = new QueryWrapper<>();
//            wrapper.eq("zhxh", -1);
//            Page<UserPO> users = userMapper.selectPage(pagePO, wrapper);

            if(users == null) {
                //业务异常
                throw new YErrorException("没有任何数据！");
            }
            return users;
        } catch (Exception e) {
            //未知异常，通用处理
            throw new CloudException("查询出错！", e);
        }
    }

    /**
     * 保存用户信息
     * @param userDTO
     * @return
     */
    @Override
    public void saveUser(UserDTO userDTO) {
        try {
            UserEntity user = new UserEntity();
            BeanUtils.copyProperties(userDTO, user);
            user.setCreateTime(LocalDateTime.now());
            int result = userMapper.insert(user);
            if (result == 0) {
                throw new YErrorException("添加用户失败！");
            }
        } catch (Exception e) {
            //未知异常，通用处理
            throw new CloudException("用户插入出错！", e);
        }
    }
}
