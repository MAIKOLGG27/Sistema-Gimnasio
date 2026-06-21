package com.gym.asistencia_servicio.config;

import org.springframework.context.annotation.*;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                    .info(new Info()
                            .title("Microservicio de asistencias")
                            .version("0.1")
                            .description("Microservicio que permite el registro y gestión de las asistencias de los usuarios al gimnasio")
                        );
    }
}
