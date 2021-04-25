package com.sakura.cloud.demo1.controller;

import com.sakura.cloud.demo1.service.FileExportAndImportService;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @auther YangFan
 * @Date 2021/1/11 15:08
 */

@RestController
@RequestMapping("/sakura")
@Api(value = "文档导入导出", tags = {"文档导入导出"})
public class FileExportAndImportController {

    private static final Logger log = LoggerFactory.getLogger(FileExportAndImportController.class);

    @Autowired
    FileExportAndImportService extAndImtService;

    @ApiOperation(value = "导出用户信息", notes = "导出用户信息", httpMethod = "POST")
    @RequestMapping(value = "/user/export", method = RequestMethod.POST)
    public CommonResult userInfoExport(@RequestHeader("loginUserOrgId") String orgId,
                                       @RequestHeader("loginUserId") String loginUserId,
                                       @RequestHeader(value = "id", required = false, defaultValue = "-1") String tenantId
                                       ) {
        extAndImtService.userInfoExport();
        return CommonResult.success();
    }

    @ApiOperation(value = "导入用户信息", notes = "导入用户信息", httpMethod = "POST")
    @RequestMapping(value = "/user/import", method = RequestMethod.POST)
    public CommonResult userInfoImport(@RequestHeader("loginUserOrgId") String orgId,
                                       @RequestHeader("loginUserId") String loginUserId,
                                       @RequestHeader(value = "id", required = false, defaultValue = "-1") String tenantId
                                       ) {
        extAndImtService.userInfoImport();
        return CommonResult.success();
    }

}
