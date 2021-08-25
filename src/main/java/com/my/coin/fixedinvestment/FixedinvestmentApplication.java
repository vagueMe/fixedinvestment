package com.my.coin.fixedinvestment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.my.coin.fixedinvestment.mapper")
public class FixedinvestmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(FixedinvestmentApplication.class, args);
    }

}
