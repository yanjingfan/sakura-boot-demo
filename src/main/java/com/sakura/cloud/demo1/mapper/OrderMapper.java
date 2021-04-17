package com.sakura.cloud.demo1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.cloud.demo1.entity.OrderPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther YangFan
 * @Date 2021/1/19 15:51
 */

@Mapper
public interface OrderMapper extends BaseMapper<OrderPO> {

}
