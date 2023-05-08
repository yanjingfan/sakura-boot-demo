package com.sakura.cloud.demo1;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @auther YangFan
 * @Date 2022/11/6 22:22
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class PasswordTest {
    @Autowired
    private StringEncryptor encryptor;

    /**
     * 生成加密密码
     */
    @Test
    public void testGeneratePassword() {
        // 你的邮箱密码
        String root = "root";
        String password = "admin";
//        String password = "Sinvieemt@123";
//        String password = "Sinvieemt@123";
        // 加密后的密码(注意：配置上去的时候需要加 ENC(加密密码))
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("sinvie2022"); //这里是密钥
        config.setAlgorithm(StandardPBEByteEncryptor.DEFAULT_ALGORITHM);
        config.setPoolSize("1");
        PooledPBEStringEncryptor encryptOr = new PooledPBEStringEncryptor();
        encryptOr.setConfig(config);
        String rootResult = encryptOr.encrypt(root);
        String passwordResult = encryptOr.encrypt(password);
        System.out.println("root: " + rootResult); //得到加密完的内容
        System.out.println("password: " + passwordResult); //得到加密完的内容
    }
}
