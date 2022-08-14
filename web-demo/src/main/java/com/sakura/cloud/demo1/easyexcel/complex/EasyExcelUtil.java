package com.sakura.cloud.demo1.easyexcel.complex;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.sakura.common.exception.YErrorException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * @auther YangFan
 * @Date 2022/8/14 12:53
 */
@Slf4j
public class EasyExcelUtil {
    private EasyExcelUtil() {
    }

    /**
     * 根据easyexcel注解给指定实体赋值
     *
     * @param objects 读取的表格内容
     * @param clazz   需转化的实体
     * @param <T>     实体
     * @return 需转化的提示集合
     */
    public static <T> List<T> convertList(List<LinkedHashMap> objects, Class<T> clazz) {
        List<T> results = new ArrayList<>(objects.size());
        try {
            Map<String, Field> objIndex = new HashMap<>();
            // 获取转化实体字段信息集合
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                // 根据实体上Easy Excel的ExcelProperty注解中的索引值对应excel读取数据的值
                int index = field.getAnnotation(ExcelProperty.class).index();
                // 设置字段可编辑
                field.setAccessible(true);
                objIndex.put(String.valueOf(index), field);
            }

            T obj = null;
            for (LinkedHashMap o : objects) {
                obj = clazz.newInstance();
                for (Object key : o.keySet()) {
                    // 如果表格索引与字段注解指定索引一样则赋值
                    if (objIndex.containsKey(key)) {
                        Object object = o.get(key);
                        Object value = null;
                        Field field = objIndex.get(key);
                        if (ObjectUtil.isEmpty(object)) {
                            continue;
                        }
                        Class<?> type = field.getType();
                        String replace = object.toString();
                        // 有特殊需要处理的字段类型则在此进行处理
                        if (type == BigDecimal.class) {
                            value = "--".equals(replace) ? null : new BigDecimal(replace.replace(",", ""));
                        } else if (type == Integer.class) {
                            // String强转Integer会报错，所以需要单独进行转化
                            value = "--".equals(replace) ? null : Integer.valueOf(replace.replace(",", ""));
                        } else {
                            value = object;
                        }
                        field.set(obj, value);
                    }
                }
                results.add(obj);
            }
        } catch (Exception e) {
            log.error("字段解析失败", e);
            throw new YErrorException("字段解析失败:" + e.getMessage());
        }
        return results;
    }
}

