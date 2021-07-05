package com.sakura.cloud.demo1.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.cloud.demo1.excel.UserExcel;
import com.sakura.cloud.demo1.mapper.UserMapper;
import com.sakura.cloud.demo1.service.FileExportAndImportService;
import com.sakura.cloud.demo1.vo.UserVO;
import com.sakura.common.exception.YErrorException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 文档导入导出
 *
 * @auther YangFan
 * @Date 2021/1/12 11:45
 */
@Service
public class FileExportAndImportServiceImpl implements FileExportAndImportService {

    @Resource
    UserMapper userMapper;

    /**
     * 导出用户信息
     * @throws IOException
     */
    @Override
    public void userInfoExport() {
        Page<UserVO> page = new Page<>(1, 10);
        List<UserVO> users = userMapper.queryUsers(page).getRecords();

        List<UserExcel> userExcels = new ArrayList<>();
        users.stream().forEach(user -> {
            UserExcel userExcel = new UserExcel();
            BeanUtils.copyProperties(user, userExcel);
            userExcels.add(userExcel);
        });
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
                UserExcel.class, userExcels);
        File savefile = new File("D:/home/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("D:/home/excel/testStudentList.xls");
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new YErrorException("导出用户信息时找不到文件!");
        } catch (IOException e) {
            throw new YErrorException("导出用户信息发生io错误!");
        } catch (Exception e) {
            throw new YErrorException("导出用户信息时出错!");
        }
    }

    /**
     * 导入用户信息
     * @throws IOException
     */
    @Override
    public void userInfoImport() {
        try {
            ImportParams params = new ImportParams();
            params.setHeadRows(2);
            List<UserExcel> list = ExcelImportUtil.importExcel(
                    new File("D:/home/excel/testStudentList.xls"), UserExcel.class, params);
            //将导入的数据存入数据库......
            //打印出来看看
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            list.forEach(in-> System.out.println(in));
        } catch (Exception e) {
            throw new YErrorException("导入用户信息时出错!");
        }
    }
}