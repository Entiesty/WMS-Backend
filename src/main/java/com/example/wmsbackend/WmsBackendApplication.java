package com.example.wmsbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.wmsbackend.mapper")
public class WmsBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(WmsBackendApplication.class, args);
    }
}
