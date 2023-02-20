package com.exchange.payingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PayingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayingServiceApplication.class, args);
    }

}
