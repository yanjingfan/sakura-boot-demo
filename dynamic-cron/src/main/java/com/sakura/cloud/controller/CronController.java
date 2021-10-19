package com.sakura.cloud.controller;

import com.sakura.common.cron.SysJob;
import com.sakura.common.cron.SysJobPO;
import com.sakura.common.cron.SysJobService;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther yangfan
 * @date 2021/10/18
 * @describle 定时任务crud
 */
@RestController
@RequestMapping("/sakura")
@Api(value = "定时任务crud", tags = {"定时任务crud"})
public class CronController {

    private static final Logger log = LoggerFactory.getLogger(CronController.class);

    @Resource
    SysJobService sysJobService;

    @ApiOperation("添加定时任务")
    @PostMapping("/cron/add")
    public CommonResult<String> add(SysJob sysJob)  {
        sysJobService.addSysJob(sysJob);
        return CommonResult.success("成功添加定时任务!");
    }

    @ApiOperation("编辑定时任务")
    @PostMapping("/cron/edit")
    public CommonResult<String> edit(SysJob sysJob)  {
        sysJobService.editSysJob(sysJob);
        return CommonResult.success("成功编辑定时任务!");
    }

    @ApiOperation("删除定时任务")
    @PostMapping("/cron/delete")
    public CommonResult<String> delete(Integer jobId)  {
        sysJobService.deleteSysJobById(jobId);
        return CommonResult.success("成功删除定时任务!");
    }

    @ApiOperation("开启或暂停定时任务")
    @PostMapping("/cron/startOrStop")
    public CommonResult<String> startOrStop(Integer jobId, Integer jobStatus)  {
        sysJobService.startOrStopSysJob(jobId, jobStatus);
        return CommonResult.success("成功开启或暂停定时任务!");
    }

    @ApiOperation("查找所有的定时任务")
    @PostMapping("/cron/list")
    public CommonResult<List<SysJobPO>> list()  {
        List<SysJobPO> jobs = sysJobService.list();
        return CommonResult.success(jobs);
    }

}
