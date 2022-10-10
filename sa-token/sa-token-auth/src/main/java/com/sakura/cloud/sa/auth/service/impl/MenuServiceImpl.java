package com.sakura.cloud.sa.auth.service.impl;

import com.sakura.cloud.sa.auth.entity.Menu;
import com.sakura.cloud.sa.auth.mapper.MenuMapper;
import com.sakura.cloud.sa.auth.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author yangfan
 * @since 2022-10-10
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
