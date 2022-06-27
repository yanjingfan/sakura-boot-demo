package com.sakura.cloud.account.service.impl;

import com.sakura.cloud.account.entity.Account;
import com.sakura.cloud.account.mapper.AccountMapper;
import com.sakura.cloud.account.service.IAccountService;
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
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

}
