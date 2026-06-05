package com.gym.pago_servicio.config;


import org.springframework.context.annotation.*;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo(){
        return new OpenAPI()
                    .info(new Info()
                            .title("Microservicio de pagos")
                            .version("0.0.2")
                            .description("Servicio que permito el ingreso y registro de los pagos")
                        );
    }
}
