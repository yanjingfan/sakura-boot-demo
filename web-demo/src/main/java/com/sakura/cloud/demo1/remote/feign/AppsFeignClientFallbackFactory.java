package com.sakura.cloud.demo1.remote.feign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakura.cloud.demo1.remote.AppsServiceFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppsFeignClientFallbackFactory implements FallbackFactory<AppsServiceFeignClient> {

    private static final Logger logger = LoggerFactory.getLogger(AppsFeignClientFallbackFactory.class);


    @Override
    public AppsServiceFeignClient create(Throwable cause) {
        ObjectMapper mapper = new ObjectMapper();

        return new AppsServiceFeignClient() {
            @Override
            public String queryUsers(Long page, Long pageSize) throws JsonProcessingException {
                Map<String, Object> resMap = new HashMap<>();
                resMap.put("code", 500);
                resMap.put("message", "接口调用失败，请检查应用服务是否启动！");
                return mapper.writeValueAsString(resMap);
            }
        };
    }
}

