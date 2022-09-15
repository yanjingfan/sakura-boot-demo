package com.sakura.cloud.sa.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.sakura"})//扫描注入的bean
public class SatokenGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SatokenGatewayApplication.class, args);
    }

}
