package com.sakura.cloud.demo1.easyexcel.complex;

import lombok.Data;

import java.util.List;

/**
 * @auther YangFan
 * @Date 2022/8/14 15:53
 */
@Data
public class FirstTarget {

    private String firstTargetName;

    private String firstTargetType;

    private List<SecondTarget> secondTargets;

}
