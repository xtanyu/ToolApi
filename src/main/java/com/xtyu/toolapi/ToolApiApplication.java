package com.xtyu.toolapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xtyu.toolapi.mapper")
public class ToolApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToolApiApplication.class, args);
    }
}
