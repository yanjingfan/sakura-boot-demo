package com.sakura.cloud.demo1.easyexcel.complex;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.sakura.common.result.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @ApiOperation("复杂excel导入")
    @PostMapping(value = "/complex/upload")
    public void upload(@RequestParam("file") MultipartFile file){
        // step1. 读取excel内容
        EasyExcelListener easyExcelListener = new EasyExcelListener();
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcelFactory.read(file.getInputStream(), easyExcelListener).build();
        } catch (IOException e) {

        }
        // step2. 获取各个sheet页信息
        List<ReadSheet> sheets = excelReader.excelExecutor().sheetList();
        // step3. 获取各个Shhet页表格内容存于map
        Map<Integer, List<LinkedHashMap<String, String>>> sheetInfos = new HashMap<>(sheets.size());
        for (ReadSheet sheet : sheets) {
            Integer sheetNo = sheet.getSheetNo();
            excelReader.read(sheet);
            sheetInfos.put(sheetNo, easyExcelListener.getListMap());
        }
        projectEasyExcelService.saveExcelInfo(sheetInfos);
    }
}
