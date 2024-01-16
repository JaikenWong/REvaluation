package com.hongyu.revaluation;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;

/**
 * @author JaikenWong
 * @since 2024-01-16 11:32
 **/
@Slf4j
public class JasyptTest {

    public static void main(String[] args) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        encryptor.setPassword("RobustnessEvaluation");
        encryptor.setIvGenerator(new RandomIvGenerator());

        // 加密
        String encryptText = encryptor.encrypt("123456");
        log.info("加密后的信息：{}", encryptText);

        // 解密
        String decryptText = encryptor.decrypt(encryptText);
        log.info("解密后的信息：{}", decryptText);
    }
}
