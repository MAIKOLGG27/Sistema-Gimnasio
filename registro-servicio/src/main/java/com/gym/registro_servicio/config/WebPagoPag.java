package com.gym.registro_servicio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebPagoPag {

    @Value("${pago.servicio.url}")
    private String pagoUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(pagoUrl)
                .build();
    }
}
