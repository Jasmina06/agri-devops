package com.agrifood.platform.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PaymentFinancialServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentFinancialServiceApplication.class, args);
    }

}