package com.sakura.cloud.oauth.gateway.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @auther YangFan
 * @Date 2021/8/7 18:20
 */
@ConfigurationProperties(prefix = "security", ignoreUnknownFields = true)
public class IgnoreUrlsProperties {

    private final Ignore ignore = new Ignore();

    public Ignore getIgnore() {
        return ignore;
    }

    public static class Ignore {

        /**
         * 忽略的URL
         */
        private List<String> excludes;

        public List<String> getExcludes() {
            return excludes;
        }

        public void setExcludes(List<String> excludes) {
            this.excludes = excludes;
        }
    }

}
