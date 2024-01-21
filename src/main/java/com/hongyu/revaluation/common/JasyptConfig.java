package com.hongyu.revaluation.common;

import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JaikenWong
 * @since 2024-01-16 11:20
 **/
@Configuration
public class JasyptConfig {

    @Bean("passwordEncryptor")
    public PasswordEncryptor passwordEncryptor() {
        StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
        return encryptor;
    }
}
