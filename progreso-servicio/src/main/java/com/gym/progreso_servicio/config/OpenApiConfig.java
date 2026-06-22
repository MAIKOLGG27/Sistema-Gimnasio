package com.gym.progreso_servicio.config;

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
                        .title("API de Progreso Físico - Sistema Gimnasio")
                        .version("1.0.0")
                        .description("Documentación oficial del microservicio responsable de registrar y medir el avance, peso y porcentaje de grasa de los clientes."));
    }
}
