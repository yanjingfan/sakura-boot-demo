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
