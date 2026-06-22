package com.gym.contrato_servicio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Contratos - Sistema Gimnasio")
                        .version("1.0.0")
                        .description("Documentación oficial del microservicio encargado de gestionar los contratos y vinculaciones entre clientes y membresías."));
    }
}