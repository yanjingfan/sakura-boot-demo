package com.sakura.cloud.sa.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//开启feign
@SpringBootApplication(scanBasePackages={"com.sakura"})//扫描注入的bean
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
