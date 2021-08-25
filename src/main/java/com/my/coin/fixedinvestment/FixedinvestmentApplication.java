package com.my.coin.fixedinvestment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FixedinvestmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(FixedinvestmentApplication.class, args);
    }

}
