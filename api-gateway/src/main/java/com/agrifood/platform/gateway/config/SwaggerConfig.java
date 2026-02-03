package com.viticulture.platform.gateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development server");

        return new OpenAPI()
                .info(new Info()
                        .title("Digital Procurement Platform API Gateway")
                        .version("1.0.0")
                        .description("API Gateway for the Digital Procurement Platform for Agriculture & Food Production"))
                .servers(List.of(server));
    }
}