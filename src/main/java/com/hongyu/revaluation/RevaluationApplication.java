package com.hongyu.revaluation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@MapperScan("com.hongyu.revaluation.mapper")
@EnableEncryptableProperties
public class RevaluationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RevaluationApplication.class, args);
    }

}
