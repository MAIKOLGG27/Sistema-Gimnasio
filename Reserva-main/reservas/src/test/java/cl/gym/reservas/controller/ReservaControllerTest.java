package cl.gym.reservas.controller;

import cl.gym.reservas.assembler.ReservaAssembler;
import cl.gym.reservas.model.Reserva;
import cl.gym.reservas.service.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservaController.class)
@Import(ReservaAssembler.class)
@DisplayName("Tests unitarios - ReservaController")
class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReservaService reservaService;

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reserva = new Reserva();
        reserva.setCodigoReserva(1L);
        reserva.setClienteId(1L);
        reserva.setNombreCliente("Juan Perez");
        reserva.setNombreClase("Yoga");
        reserva.setInstructor("Maria Lopez");
        reserva.setFechaHoraClase(LocalDateTime.now().plusDays(1));
        reserva.setDuracionMinutos(60);
        reserva.setCapacidadMaxima(20);
        reserva.setEstado("CONFIRMADA");
        reserva.setFechaReserva(LocalDateTime.now());
    }

    private Reserva buildReservaSinId() {
        Reserva r = new Reserva();
        r.setClienteId(1L);
        r.setNombreClase("Pilates");
        r.setInstructor("Carlos Ruiz");
        r.setFechaHoraClase(LocalDateTime.now().plusDays(2));
        r.setDuracionMinutos(45);
        r.setCapacidadMaxima(15);
        return r;
    }

    @Test
    @DisplayName("GIVEN: reservas en BD WHEN: GET /api/reservas THEN: 200")
    void shouldReturn200_whenGetAll() throws Exception {
        when(reservaService.obtenerTodos()).thenReturn(List.of(reserva));

        mockMvc.perform(get("/api/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.reservaList[0].nombreClase").value("Yoga"));
    }

    @Test
    @DisplayName("GIVEN: ID existente WHEN: GET /api/reservas/{id} THEN: 200")
    void shouldReturn200_whenGetById() throws Exception {
        when(reservaService.obtenerPorId(1L)).thenReturn(Optional.of(reserva));

        mockMvc.perform(get("/api/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreClase").value("Yoga"));
    }

    @Test
    @DisplayName("GIVEN: ID inexistente WHEN: GET /api/reservas/{id} THEN: 404")
    void shouldReturn404_whenGetByIdNotFound() throws Exception {
        when(reservaService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/reservas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GIVEN: clienteId existente WHEN: GET /api/reservas/cliente/{clienteId} THEN: 200")
    void shouldReturn200_whenGetByClienteId() throws Exception {
        when(reservaService.obtenerPorClienteId(1L)).thenReturn(List.of(reserva));

        mockMvc.perform(get("/api/reservas/cliente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreCliente").value("Juan Perez"));
    }

    @Test
    @DisplayName("GIVEN: estado existente WHEN: GET /api/reservas/estado/{estado} THEN: 200")
    void shouldReturn200_whenGetByEstado() throws Exception {
        when(reservaService.obtenerPorEstado("CONFIRMADA")).thenReturn(List.of(reserva));

        mockMvc.perform(get("/api/reservas/estado/CONFIRMADA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("CONFIRMADA"));
    }

    @Test
    @DisplayName("GIVEN: reserva valida WHEN: POST /api/reservas THEN: 201")
    void shouldReturn201_whenCreate() throws Exception {
        when(reservaService.guardar(any())).thenReturn(reserva);

        mockMvc.perform(post("/api/reservas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(buildReservaSinId())))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GIVEN: nombreClase vacio WHEN: POST /api/reservas THEN: 400")
    void shouldReturn400_whenCreateWithInvalidData() throws Exception {
        Reserva invalida = buildReservaSinId();
        invalida.setNombreClase("");

        mockMvc.perform(post("/api/reservas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalida)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GIVEN: ID existente WHEN: PUT /api/reservas/{id} THEN: 200")
    void shouldReturn200_whenUpdate() throws Exception {
        when(reservaService.actualizar(eq(1L), any())).thenReturn(Optional.of(reserva));

        mockMvc.perform(put("/api/reservas/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(buildReservaSinId())))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GIVEN: ID inexistente WHEN: PUT /api/reservas/{id} THEN: 404")
    void shouldReturn404_whenUpdateNotFound() throws Exception {
        when(reservaService.actualizar(eq(99L), any())).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/reservas/99")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(buildReservaSinId())))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GIVEN: ID existente WHEN: DELETE /api/reservas/{id} THEN: 204")
    void shouldReturn204_whenDelete() throws Exception {
        when(reservaService.obtenerPorId(1L)).thenReturn(Optional.of(reserva));

        mockMvc.perform(delete("/api/reservas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GIVEN: ID inexistente WHEN: DELETE /api/reservas/{id} THEN: 404")
    void shouldReturn404_whenDeleteNotFound() throws Exception {
        when(reservaService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/reservas/99"))
                .andExpect(status().isNotFound());
    }
}