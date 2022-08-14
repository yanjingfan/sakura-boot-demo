package com.sakura.cloud.demo1.easyexcel.complex;

import lombok.Data;

import java.util.List;

/**
 * @auther YangFan
 * @Date 2022/8/14 15:53
 */
@Data
public class SecondTarget {

    private String secondTargetName;

    private List<ThirdTarget> thirdTargets;

}
