package com.sakura.cloud;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * Base64和加密解密
 *
 * @auther YangFan
 * @Date 2021/1/12 10:52
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class Base64AndDigestUtilsTest {

    @Test
    public void testBase64() {
        System.out.println("===============base64======================");
        byte[] data = "sakura-demo".getBytes();
        Base64 base64 = new Base64();
        //转码
        String encode = base64.encodeAsString(data);
        System.out.println("base64加密后：" + encode);
        //解码
        String decode = new String(base64.decode(encode));
        System.out.println("base64解密后：" + decode);
    }

    /**
     * DigestUtils工具类里有多种加密的方式，自行选择
     */
    @Test
    public void testDigestUtils() {
        System.out.println("===============testMD5======================");
        String result = DigestUtils.md5Hex("sakura-demo-md5");
        System.out.println("md5加密后：" + result);

        System.out.println("===============testsha256Hex======================");
        String sha256Hex = DigestUtils.sha256Hex("sakura-demo-sha256");
        System.out.println("sha256加密后：" + sha256Hex);
    }

}
