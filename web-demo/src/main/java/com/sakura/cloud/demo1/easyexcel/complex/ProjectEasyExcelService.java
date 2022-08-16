package com.sakura.cloud.demo1.easyexcel.complex;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.sakura.cloud.demo1.entity.ProjectManage;
import com.sakura.cloud.demo1.service.IProjectManageService;
import com.sakura.common.exception.YErrorException;
import com.sakura.common.exception.YWarmingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @auther yangfan
 * @date 2022/3/23
 * @describle
 */
@Slf4j
@Service
public class ProjectEasyExcelService {

    @Autowired
    private IProjectManageService projectManageService;

    private Long projectId = 0L;

    /**
     * 项目信息excel
     */
    @Transactional(rollbackFor = Exception.class)
    public void projectRead(MultipartFile file) {
        EasyExcelListener easyExcelListener = new EasyExcelListener();
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcelFactory.read(file.getInputStream(), easyExcelListener).build();
        } catch (IOException e) {
            throw new YErrorException("项目信息导入出错！");
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
        //保存数据到数据库
        saveExcelInfo(sheetInfos);
    }

    public void saveExcelInfo(Map<Integer, List<LinkedHashMap<String, String>>> sheetInfos) {
        for (Integer sheetNo : sheetInfos.keySet()) {
            List<LinkedHashMap<String, String>> maps = sheetInfos.get(sheetNo);
            // 不同sheet页数据处理方式不同
            switch (sheetNo) {
                case 0:
                    saveProject(maps);
                    break;
                case 1:
                    saveTarget(maps);
                    break;
                default:
                    break;
            }
        }
    }

    public void saveProject(List<LinkedHashMap<String, String>> maps) {
        ProjectManage projectManage = new ProjectManage();
        for (LinkedHashMap<String, String> map : maps) {
            if (map.containsValue("项目名称")) {
                String projectName = map.getOrDefault("1", "");
                if (StringUtils.isBlank(projectName)) {
                    throw new YWarmingException("项目名称不能为空！");
                }
                projectManage.setProjectName(projectName);
            }
            if (map.containsValue("项目编号")) {
                String projectCode = map.getOrDefault("4", "");
                projectManage.setProjectCode(projectCode);
            }
            if (map.containsValue("合同签订时间")) {
                String contractSignTimeStr = map.getOrDefault("1", "");
                if (StringUtils.isNotBlank(contractSignTimeStr)) {
                    Date contractSignDate = DateUtil.parse(contractSignTimeStr);
                    Instant instant = contractSignDate.toInstant();
                    LocalDateTime contractSignTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                    projectManage.setContractSignTime(contractSignTime);
                }
            }
            if (map.containsValue("合同规定终验时间")) {
                String contractRuleFinalAcceptTimeStr = map.getOrDefault("3", "");
                if (StringUtils.isNotBlank(contractRuleFinalAcceptTimeStr)) {
                    Date contractSignDate = DateUtil.parse(contractRuleFinalAcceptTimeStr);
                    Instant instant = contractSignDate.toInstant();
                    LocalDateTime contractRuleFinalAcceptTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                    projectManage.setContractRuleFinalAcceptTime(contractRuleFinalAcceptTime);
                }
            }
            if (map.containsValue("开工时间")) {
                String startTimeStr = map.getOrDefault("5", "");
                if (StringUtils.isNotBlank(startTimeStr)) {
                    Date startDate = DateUtil.parse(startTimeStr);
                    Instant instant = startDate.toInstant();
                    LocalDateTime startTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                    projectManage.setStartTime(startTime);
                }
            }

            if (map.containsValue("开工模式")) {
                //查询字段表
                String startWorkModel = map.getOrDefault("1", "");
            }
            if (map.containsValue("渠道来源")) {
                //查询字典表
                String channelSource = map.getOrDefault("4", "");
            }
            if (map.containsValue("销售部门")) {
                String projectCode = map.getOrDefault("1", "");
                projectManage.setProjectCode(projectCode);
            }
            if (map.containsValue("产品线")) {
                //查询字段表
                String productLine = map.getOrDefault("4", "");
            }
            if (map.containsValue("合同额")) {
                String contractAmount = map.getOrDefault("1", "");
                projectManage.setContractAmount(contractAmount);
            }
            if (map.containsValue("合同额-产品")) {
                String contractAmountProd = map.getOrDefault("1", "");
                projectManage.setContractAmountProd(contractAmountProd);
            }
            if (map.containsValue("合同额-集成")) {
                String contractAmountGather = map.getOrDefault("1", "");
                projectManage.setContractAmountGather(contractAmountGather);
            }
            if (map.containsValue("合同额-服务")) {
                String contractAmountService = map.getOrDefault("1", "");
                projectManage.setContractAmountService(contractAmountService);
            }
        }
        //这里项目背景的行数，写死了，后面调整表格时，会影响这一块
        Map<String, String> projectBackgroudMap = maps.get(8);
        Map<String, String> projectDescMap = maps.get(10);
        Map<String, String> projectNeedsMap = maps.get(12);
        Map<String, String> projectRiskMap = maps.get(14);
        String projectBackgroud = projectBackgroudMap.getOrDefault("0", "").toString();
        projectManage.setProjectBackgroud(projectBackgroud);
        String projectDesc = projectDescMap.getOrDefault("0", "").toString();
        projectManage.setProjectDesc(projectDesc);
        String projectNeeds = projectNeedsMap.getOrDefault("0", "").toString();
        projectManage.setProjectNeeds(projectNeeds);
        String projectRisk = projectRiskMap.getOrDefault("0", "").toString();
        projectManage.setProjectRisk(projectRisk);
        projectManageService.save(projectManage);
        projectId = projectManage.getId();
    }

    public void saveTarget(List<LinkedHashMap<String, String>> list) {
        if (projectId == 0) {
            throw new YErrorException("请先成功导入项目！");
        }

        FirstTarget firstTarget = new FirstTarget();
        SecondTarget secondTarget = new SecondTarget();
        List<FirstTarget> firstTargetList = new ArrayList<>();
        List<SecondTarget> secondTargetList = new ArrayList<>();
        List<ThirdTarget> thirdTargetList = new ArrayList<>();

        for (Map<String, String> map : list) {
            System.err.println(map);
            String firstTargetName = map.getOrDefault("0", "");
            String firstTargetType = map.getOrDefault("1", "");
            String secondTargetName = map.getOrDefault("2", "");
            String thirdTargetName = map.getOrDefault("3", "");
            String thirdTargetBudget = map.getOrDefault("4", "");
            String positions = map.getOrDefault("5", "");

            if (StringUtils.isNotBlank(firstTargetName)) {
                //第一阶段名称不为空时，二三阶段一定不为空
                if (StringUtils.isNotBlank(secondTargetName)) {
                    //第二阶段名称不为空
                    if (secondTargetList.size() != 0) {
                        List<SecondTarget> secondTargets = firstTarget.getSecondTargets();
                        secondTargets.addAll(secondTargetList);
                    }

                    secondTarget = new SecondTarget();
                    thirdTargetList = new ArrayList<>();

                    firstTarget = new FirstTarget();
                    secondTargetList = new ArrayList<>();

                    secondTarget.setSecondTargetName(secondTargetName);

                    ThirdTarget thirdTarget = new ThirdTarget();
                    thirdTarget.setThirdTargetName(thirdTargetName);
                    thirdTarget.setThirdTargetBudget(thirdTargetBudget);
                    thirdTargetList.add(thirdTarget);

                    secondTarget.setThirdTargets(thirdTargetList);
                    secondTargetList.add(secondTarget);

                    firstTarget.setSecondTargets(secondTargetList);
                    firstTarget.setFirstTargetName(firstTargetName);
                    firstTarget.setFirstTargetType(firstTargetType);
                    firstTargetList.add(firstTarget);
                }
            } else {
                //第一阶段名称为空，且第二阶段名称不为空
                if (StringUtils.isNotBlank(secondTargetName)) {
                    //去重
                    if (secondTargetList.size() != 0) {
                        SecondTarget st = secondTargetList.get(0);
                        List<SecondTarget> secondTargets = firstTarget.getSecondTargets();
                        SecondTarget secondT = secondTargets.get(0);
                        if (!st.getSecondTargetName().equals(secondT.getSecondTargetName())) {
                            secondTargets.addAll(secondTargetList);
                        }
                    }

                    secondTarget = new SecondTarget();
                    thirdTargetList = new ArrayList<>();

                    secondTargetList = new ArrayList<>();

                    secondTarget.setSecondTargetName(secondTargetName);

                    ThirdTarget thirdTarget = new ThirdTarget();
                    thirdTarget.setThirdTargetName(thirdTargetName);
                    thirdTarget.setThirdTargetBudget(thirdTargetBudget);
                    thirdTargetList.add(thirdTarget);

                    secondTarget.setSecondTargetName(secondTargetName);
                    secondTarget.setThirdTargets(thirdTargetList);
                    secondTargetList.add(secondTarget);
                } else {
                    //第一阶段名称为空，第二阶段名称为空，第三阶段不为空
                    ThirdTarget thirdTarget = new ThirdTarget();
                    thirdTarget.setThirdTargetBudget(thirdTargetBudget);
                    thirdTarget.setThirdTargetName(thirdTargetName);
                    thirdTargetList.add(thirdTarget);
                }
            }
        }
        // 保存最后一条数据
        if (secondTargetList.size() != 0) {
            List<SecondTarget> secondTargets = firstTarget.getSecondTargets();
            secondTargets.addAll(secondTargetList);
        }
        System.err.println(firstTargetList);
    }

}
