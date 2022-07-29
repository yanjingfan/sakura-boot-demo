package com.sakura.cloud.oauth.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.sakura"})//扫描注入的bean
public class OAuth2GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2GatewayApplication.class, args);
    }

}
