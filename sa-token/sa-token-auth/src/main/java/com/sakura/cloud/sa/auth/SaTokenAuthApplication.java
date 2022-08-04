package com.sakura.cloud.sa.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.sakura"})//扫描注入的bean
public class SaTokenAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaTokenAuthApplication.class, args);
    }

}
