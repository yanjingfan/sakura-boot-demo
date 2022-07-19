package com.sakura.cloud.gateway.config;

import com.sakura.cloud.gateway.property.IgnoreUrlsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 网关白名单配置
 */
@Configuration
@EnableConfigurationProperties(IgnoreUrlsProperties.class)
public class IgnoreUrlsConfig {
}
