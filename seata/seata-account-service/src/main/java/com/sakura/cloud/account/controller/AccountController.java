package com.sakura.cloud.account.controller;


import com.sakura.cloud.account.service.IAccountService;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangfan
 * @since 2022-06-27
 */
@RestController
@RequestMapping("/account")
@Api(value = "账户余额操作API", tags = {"账户余额操作API"})
public class AccountController {

    @Autowired
    private IAccountService accountService;

    /**
     * 扣减账户余额
     * @return
     */
    @PostMapping("/decrease")
    @ApiOperation("扣减账户余额")
    public CommonResult<String> decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money){
        accountService.decrease(userId,money);
        return CommonResult.success("扣减账户余额成功！");
    }
}

