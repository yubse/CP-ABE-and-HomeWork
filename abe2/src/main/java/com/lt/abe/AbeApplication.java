package com.lt.abe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lt.abe.dao")
public class AbeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbeApplication.class, args);
    }

}
