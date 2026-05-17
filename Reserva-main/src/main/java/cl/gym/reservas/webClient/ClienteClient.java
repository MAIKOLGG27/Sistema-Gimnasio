package cl.gym.reservas.webClient;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClienteClient {

     private final WebClient webClient;

     public String obtenerNombreCliente(Long clienteId) {
        try {
            Map response = webClient
                    .get()
                    .uri("/api/usuarios/" + clienteId)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            return response != null ? (String) response.get("nombre") : "Sin cliente";
        } catch (Exception e) {
            return "Sin cliente";
        }
    }

}
