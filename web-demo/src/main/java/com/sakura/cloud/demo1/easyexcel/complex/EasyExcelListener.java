package com.sakura.cloud.demo1.easyexcel.complex;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther yangfan
 * @date 2022/3/23
 * @describle
 */
// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
@Slf4j
public class EasyExcelListener extends AnalysisEventListener<Object> {

    // 创建list集合封装最终的数据
    private List<Object> list = new ArrayList<>();
    // sheet页索引
    private int sheetNo = 0;

    @Override
    public void invoke(Object t, AnalysisContext context) {
        // 读取excle内容
        int currentSheetNo = context.readSheetHolder().getSheetNo();
        if (currentSheetNo != sheetNo) {
            // 如果不根据sheet页索引更新状态重新创建list，list会反复添加前面的sheet页对象值
            list = new ArrayList<>();
            sheetNo = currentSheetNo;
        }
        list.add(t);
    }

    // 读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
    }

    // 读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

    /**
     * 获取表格内容(简单excel读取可用该方法)
     *
     * @param obj 需要转化的实体
     * @param <T>
     * @return
     */
    public <T> List<T> getList(Class<T> obj) {
        String jsonObj = JSON.toJSONString(list);
        return JSON.parseArray(jsonObj, obj);
    }

    /**
     * 将表格转化为map集合（复杂excel读取用此方法）
     *
     * @return map集合
     */
    public List<LinkedHashMap<String, String>> getListMap() {
        String jsonObj = JSON.toJSONString(list);
        return JSON.parseArray(jsonObj, LinkedHashMap.class);
    }

}

