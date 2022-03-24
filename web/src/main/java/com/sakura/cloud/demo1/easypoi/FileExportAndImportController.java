package com.sakura.cloud.demo1.easypoi;

import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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

    @ApiOperation(value = "导出用户信息(先导出)", notes = "导出用户信息(先导出)")
    @PostMapping(value = "/user/export")
    public CommonResult<Object> userInfoExport(@RequestHeader("loginUserOrgId") String orgId,
                                       @RequestHeader("loginUserId") String loginUserId,
                                       @RequestHeader(value = "id", required = false, defaultValue = "-1") String tenantId
    ) {
        extAndImtService.userInfoExport();
        return CommonResult.success();
    }

    @ApiOperation(value = "导入用户信息(先要导出文档)", notes = "导入用户信息(先要导出文档)")
    @PostMapping(value = "/user/import")
    public CommonResult<Object> userInfoImport(@RequestHeader("loginUserOrgId") String orgId,
                                       @RequestHeader("loginUserId") String loginUserId,
                                       @RequestHeader(value = "id", required = false, defaultValue = "-1") String tenantId
    ) {
        extAndImtService.userInfoImport();
        return CommonResult.success();
    }

    @ApiOperation(value = "浏览器导出excel文件", notes = "浏览器导出excel文件")
    @GetMapping(value = "/user/excel/download")
    public CommonResult<Object> userInfoDownload(HttpServletResponse response) {
        extAndImtService.userInfoDownload(response);
        return CommonResult.success();
    }

}