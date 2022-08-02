package com.sakura.cloud.sa.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//开启feign
@SpringBootApplication(scanBasePackages={"com.sakura"})//扫描注入的bean
public class SaTokenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaTokenApplication.class, args);
    }

}
