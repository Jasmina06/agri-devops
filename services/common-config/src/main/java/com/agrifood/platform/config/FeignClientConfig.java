package com.agrifood.platform.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableFeignClients
@Import({FeignConfig.class, SecurityConfig.class})
public class FeignClientConfig {
}