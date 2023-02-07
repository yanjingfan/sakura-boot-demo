package com.sakura.cloud.demo1.sign;

import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.SortedMap;

/**
 * @auther yangfan
 * @date 2023/2/6
 * @describle
 */
@Slf4j
@UtilityClass
public class SignUtil {
    /**
     * 验证签名
     * 验证算法：把timestamp + JsonUtil.object2Json(SortedMap)合成字符串，然后MD5
     */
    @SneakyThrows
    public  boolean verifySign(SortedMap<String, String> map, RequestHeader requestHeader) {
        String params = requestHeader.getNonce() + requestHeader.getTimestamp() + JSONUtil.toJsonStr(map);
        return verifySign(params, requestHeader);
    }

    /**
     * 验证签名
     */
    public boolean verifySign(String params, RequestHeader requestHeader) {
        log.debug("客户端签名: {}", requestHeader.getSign());
        if (StringUtils.isEmpty(params)) {
            return false;
        }
        log.info("客户端上传内容: {}", params);
        String paramsSign = DigestUtils.md5DigestAsHex(params.getBytes()).toUpperCase();
        log.info("客户端上传内容加密后的签名结果: {}", paramsSign);
        return requestHeader.getSign().equals(paramsSign);
    }

    public static void main(String[] args) {
        String params =  "1234561676674500{}";
        String paramsSign = DigestUtils.md5DigestAsHex(params.getBytes()).toUpperCase();
        System.err.println(paramsSign);
    }
}
