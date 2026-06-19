package com.gym.pago_servicio.config;


import org.springframework.context.annotation.*;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo(){

        final String securitySchemeName = "basicAuth";
        return new OpenAPI()
                    .info(new Info()
                            .title("Microservicio de pagos")
                            .version("0.1")
                            .description("Microservicio que permito el ingreso y registro de los pagos"))
                    .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                    .components(new Components()
                            .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic")
                                .description("Ingresar las credenciales: admin / 1234")
                            ))

                        ;
    }
}
