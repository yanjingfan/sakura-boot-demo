package com.sakura.cloud.demo1.sign;

import lombok.Builder;
import lombok.Data;

/**
 * @auther yangfan
 * @date 2023/2/6
 * @describle
 */
@Data
@Builder
public class RequestHeader {
    private String sign ;
    private Long timestamp ;
    private String nonce;
}
