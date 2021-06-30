package com.sakura.cloud.uid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.sakura"})//扫描注入的bean
public class UidApplication {

    public static void main(String[] args) {
        SpringApplication.run(UidApplication.class, args);
    }

}
