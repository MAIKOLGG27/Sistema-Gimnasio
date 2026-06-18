package com.gym.registro_servicio.webPago;

import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

public class PagoPag {

    private final WebClient webClient;

    public Map obtenerPago(Long pagoId) {
        try {
            return webClient
                    .get()
                    .uri("/pagos/" + pagoId)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }
}

