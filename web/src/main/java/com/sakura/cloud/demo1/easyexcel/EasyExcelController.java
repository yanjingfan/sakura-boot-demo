package com.sakura.cloud.demo1.easyexcel;

import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @auther yangfan
 * @date 2022/3/23
 * @describle 官方文档：https://www.yuque.com/easyexcel/doc/easyexcel
 */
@RestController
@RequestMapping("/sakura/easyexcel")
@Api(value = "EasyExcel文档导入导出", tags = {"EasyExcel文档导入导出"})
public class EasyExcelController {
    @Autowired
    DemoEasyExcelService demoEasyExcelService;

    @ApiOperation(value = "读取excel", notes = "读取excel")
    @PostMapping(value = "/simpleRead")
    public CommonResult<Object> simpleRead() {
        demoEasyExcelService.simpleRead();
        return CommonResult.success();
    }

    @ApiOperation(value = "导入用户信息", notes = "导入用户信息")
    @PostMapping(value = "/import")
    public CommonResult<Object> userInfoImport() {
        return CommonResult.success();
    }

    @ApiOperation(value = "浏览器导出excel文件", notes = "浏览器导出excel文件")
    @PostMapping(value = "/download")
    public CommonResult<Object> userInfoDownload(HttpServletResponse response) {
        return CommonResult.success();
    }
}
