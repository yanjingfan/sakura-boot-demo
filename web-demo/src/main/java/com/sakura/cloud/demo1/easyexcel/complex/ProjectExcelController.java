package com.sakura.cloud.demo1.easyexcel.complex;

import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @auther yangfan
 * @date 2022/3/23
 * @describle 官方文档：https://www.yuque.com/easyexcel/doc/easyexcel
 */
@RestController
@RequestMapping("/sakura/easyexcel")
@Api(value = "复杂excel导入", tags = {"复杂excel导入"})
public class ProjectExcelController {
    @Autowired
    ProjectEasyExcelService projectEasyExcelService;

    /**
     * @param file 文档在sakura-boot-demo/docs/excel/项目导入.xlsx
     * @return
     */
    @ApiOperation("复杂excel导入")
    @PostMapping(value = "/complex/upload")
    public CommonResult<Object> upload(@RequestParam("file") MultipartFile file){
        projectEasyExcelService.projectRead(file);
        return CommonResult.success();
    }
}
