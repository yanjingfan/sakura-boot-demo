package com.sakura.cloud.remote;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sakura.cloud.remote.feign.AppsFeignClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * 远程服务调用
 *
 */


@FeignClient(
        name = "test-app-svc", //远程服务名
        fallbackFactory = AppsFeignClientFallbackFactory.class, //此服务接口具体处理类
        url = "http://192.168.35.12:7094"   //本地调试打开，生产环境注释掉这个url属性
)
public interface AppsServiceFeignClient {

    //远程服务接口。方法名、参数和请求类型（get请求或者是post）需要一致
    @GetMapping("/apps")
    String queryApps(@RequestParam("page") Long page,
                     @RequestParam("pageSize") Long pageSize,
                     @RequestParam("keyword") String keyword) throws JsonProcessingException;
}
