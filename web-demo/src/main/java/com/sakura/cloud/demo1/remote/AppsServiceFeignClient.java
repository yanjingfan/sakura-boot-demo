package com.sakura.cloud.demo1.remote;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sakura.cloud.demo1.dto.UserDTO;
import com.sakura.cloud.demo1.remote.feign.AppsFeignClientFallbackFactory;
import com.sakura.cloud.demo1.vo.UserVO;
import com.sakura.common.db.mp.CommonPage;
import com.sakura.common.result.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


/**
 *
 * 远程服务调用
 *
 */


@FeignClient(
        name = "web-demo", //远程服务名
        fallbackFactory = AppsFeignClientFallbackFactory.class, //此服务接口具体处理类
        url = "http://localhost:8080"   //本地调试打开，生产环境注释掉这个url属性
)
public interface AppsServiceFeignClient {

    //远程服务接口。方法名、参数和请求类型（get请求或者是post）需要一致
    @GetMapping("/sakura/users")
    String queryUsers(@RequestParam("page") Long page,
                      @RequestParam("pageSize") Long pageSize) throws JsonProcessingException;
}
