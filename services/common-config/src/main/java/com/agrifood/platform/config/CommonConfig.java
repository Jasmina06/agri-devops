package com.agrifood.platform.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.agrifood.platform")
@EnableConfigurationProperties(FeignClientProperties.class)
public class CommonConfig {
}