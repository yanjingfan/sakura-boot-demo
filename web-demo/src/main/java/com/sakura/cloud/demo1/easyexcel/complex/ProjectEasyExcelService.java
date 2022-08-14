package com.sakura.cloud.demo1.easyexcel.complex;

import com.sakura.common.exception.YErrorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther yangfan
 * @date 2022/3/23
 * @describle
 */
@Slf4j
@Service
public class ProjectEasyExcelService {

    private Long projectId = 0L;

    public void saveExcelInfo(Map<Integer, List<LinkedHashMap<String, String>>> sheetInfos) {
//        SettlementMothReportSaveDTO settlementMothReportSaveDTO = new SettlementMothReportSaveDTO();
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
        projectId = 1L;
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

//            if (StringUtils.isNotBlank(firstTargetName)) {
//                if (secondTargetList.size() != 0) {
//                    firstTarget.setSecondTargets(secondTargetList);
//                    firstTarget.setFirstTargetName(firstTargetName);
//                    firstTarget.setFirstTargetType(firstTargetType);
//                    firstTargetList.add(firstTarget);
//                }
//                firstTarget = new FirstTarget();
//                secondTargetList = new ArrayList<>();
//
//                firstTarget.setFirstTargetName(firstTargetName);
//                firstTarget.setFirstTargetType(firstTargetType);
//
//                SecondTarget secondTarget = new SecondTarget();
//                secondTarget.setSecondTargetName(secondTargetName);
//                secondTarget.setThirdTargets(thirdTargetList);
//                secondTargetList.add(secondTarget);

                if (StringUtils.isNotBlank(secondTargetName)) {
                    if (thirdTargetList.size() != 0) {
                        secondTarget.setThirdTargets(thirdTargetList);
                        secondTargetList.add(secondTarget);
                    }
                    secondTarget = new SecondTarget();
                    thirdTargetList = new ArrayList<>();

                    secondTarget.setSecondTargetName(secondTargetName);

                    ThirdTarget thirdTarget = new ThirdTarget();
                    thirdTarget.setThirdTargetName(thirdTargetName);
                    thirdTarget.setThirdTargetBudget(thirdTargetBudget);
                    thirdTargetList.add(thirdTarget);
                } else {
                    ThirdTarget thirdTarget = new ThirdTarget();
                    thirdTarget.setThirdTargetBudget(thirdTargetBudget);
                    thirdTarget.setThirdTargetName(thirdTargetName);
                    thirdTargetList.add(thirdTarget);
                }

//            } else {
//                SecondTarget secondTarget = new SecondTarget();
//                secondTarget.setSecondTargetName(secondTargetName);
//                secondTarget.setThirdTargets(thirdTargetList);
//                secondTargetList.add(secondTarget);
//            }
//            if (secondTargetList.size() != 0) {
//                firstTarget.setFirstTargetName(firstTargetName);
//                firstTarget.setFirstTargetType(firstTargetType);
//                firstTarget.setSecondTargets(secondTargetList);
//                firstTargetList.add(firstTarget);
//            }
        }
        // 保存最后一条数据
        if (thirdTargetList.size() != 0) {
            secondTarget.setThirdTargets(thirdTargetList);
            secondTargetList.add(secondTarget);
        }
        System.err.println(secondTargetList);
    }

}
