package com.sakura.cloud.sa.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.sakura"})//扫描注入的bean
public class SaTokenApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaTokenApiApplication.class, args);
    }

}
