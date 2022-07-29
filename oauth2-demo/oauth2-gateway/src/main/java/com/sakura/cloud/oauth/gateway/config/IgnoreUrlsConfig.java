package com.sakura.cloud.oauth.gateway.config;

import com.sakura.cloud.oauth.gateway.property.IgnoreUrlsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 网关白名单配置
 */
@Configuration
@EnableConfigurationProperties(IgnoreUrlsProperties.class)
public class IgnoreUrlsConfig {
}
