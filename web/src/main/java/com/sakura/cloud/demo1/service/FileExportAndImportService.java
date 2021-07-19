package com.sakura.cloud.demo1.service;

import javax.servlet.http.HttpServletResponse;

/**
 *
 * 文档导入导出
 *
 * @auther YangFan
 * @Date 2021/1/12 11:44
 */

public interface FileExportAndImportService {

    void userInfoExport();

    void userInfoImport();

    void userInfoDownload(HttpServletResponse response);
}