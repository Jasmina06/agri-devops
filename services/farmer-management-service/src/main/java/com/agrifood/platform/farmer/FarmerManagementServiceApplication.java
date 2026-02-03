package com.agrifood.platform.farmer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FarmerManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FarmerManagementServiceApplication.class, args);
    }

}