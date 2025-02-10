package com.owpai.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.owpai")
@MapperScan("com.owpai.server.mapper")
public class OwpaiCardApplication {
    public static void main(String[] args) {
        SpringApplication.run(OwpaiCardApplication.class, args);
    }
}