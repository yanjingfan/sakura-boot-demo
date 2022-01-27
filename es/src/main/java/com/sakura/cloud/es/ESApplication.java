package com.sakura.cloud.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.sakura"})//扫描注入的bean
public class ESApplication {

    public static void main(String[] args) {
        SpringApplication.run(ESApplication.class, args);
    }

}
