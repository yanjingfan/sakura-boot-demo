package com.sakura.cloud.demo1.easyexcel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @auther yangfan
 * @date 2022/3/23
 * @describle
 */
@Getter
@Setter
@EqualsAndHashCode
public class DemoData {
    private String string;
    private LocalDateTime date;
    private Double doubleData;
}
