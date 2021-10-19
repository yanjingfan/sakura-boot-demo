package com.sakura.cloud.controller;

import org.springframework.stereotype.Component;

/**
 * @auther yangfan
 * @date 2021/10/19
 * @describle
 */
@Component("demoTask")
public class DemoTask {
    public void taskWithParams(String params) {
        System.out.println("执行有参示例任务：" + params);
    }

    public void taskNoParams() {
        System.out.println("执行无参示例任务");
    }
}
