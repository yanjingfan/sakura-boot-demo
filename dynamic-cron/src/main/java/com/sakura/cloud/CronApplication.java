package com.sakura.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={"com.sakura"})//扫描注入的bean
@EnableJpaRepositories(basePackages = "com.sakura.common.cron")
@EntityScan(basePackages = "com.sakura.common.cron")
public class CronApplication {

    public static void main(String[] args) {
        SpringApplication.run(CronApplication.class, args);
    }

}
