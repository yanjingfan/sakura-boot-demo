package com.sakura.cloud.demo1.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

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

    @ApiOperation(value = "本地生成excel", notes = "本地生成excel")
    @PostMapping(value = "/simpleWrite")
    public CommonResult<Object> userInfoImport() {
        demoEasyExcelService.simpleWrite();
        return CommonResult.success();
    }

    @ApiOperation(value = "导出excel文件(浏览器直接访问链接)", notes = "导出excel文件(浏览器直接访问链接)")
    @GetMapping("download")
    public CommonResult<Object> download(HttpServletResponse response) throws IOException {
        demoEasyExcelService.download(response);
        return CommonResult.success();
    }
}
