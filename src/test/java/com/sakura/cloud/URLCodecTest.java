package com.sakura.cloud;

import org.apache.commons.codec.net.URLCodec;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * URL转码解码测试
 *
 * @auther YangFan
 * @Date 2021/1/12 10:27
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class URLCodecTest {

    @Test
    public void testURLCodec() throws Exception {
        URLCodec codec = new URLCodec();
        String data = "http://urlCode";
        //转码
        String encode = codec.encode(data, "UTF-8");
        System.out.println("url转码后的结果：" + encode);
        //解码
        String decode = codec.decode(encode, "UTF-8");
        System.out.println("url解码后的结果：" + decode);
    }
}
