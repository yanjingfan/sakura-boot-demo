package com.sakura.cloud.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @auther yangfan
 * @date 2022/2/25
 * @describle
 */
@SpringBootApplication(scanBasePackages={"com.sakura"})//扫描注入的bean
public class WebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketApplication.class, args);
    }

}
