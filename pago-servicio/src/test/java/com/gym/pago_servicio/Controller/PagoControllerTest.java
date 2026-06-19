package com.gym.pago_servicio.Controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.gym.pago_servicio.model.Pago;
import com.gym.pago_servicio.Service.PagoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// addFilters = false desactiva los filtros de Spring Security para este test,
// ya que aqui solo se evalua la logica del controller, no la autenticacion.
@WebMvcTest(PagoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagoService pagoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pago pago;

    @BeforeEach
    void setUp() {
        pago = new Pago();
        pago.setId(1L);
        pago.setSocioId(2L);
        pago.setMonto(15000.0);
        pago.setMetodoPago("Visa");
        pago.setEstado("PENDIENTE");
        pago.setFechaPago(LocalDateTime.now());
    }

    @Test
    public void testTraerTodos() throws Exception {
        when(pagoService.obtenerTodas()).thenReturn(List.of(pago));

        mockMvc.perform(get("/api/v0/pagos/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].estado").value("PENDIENTE"));
    }

    @Test
    public void testBuscarPorId() throws Exception {
        when(pagoService.buscarPorId(1L)).thenReturn(Optional.of(pago));

        mockMvc.perform(get("/api/v0/pagos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.socioId").value(2));
    }

    @Test
    public void testBuscarPorId_NoExiste() throws Exception {
        when(pagoService.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v0/pagos/99"))
                .andExpect(status().isOk())
                .andExpect(content().string("null"));
    }

    @Test
    public void testCrearPago() throws Exception {
        when(pagoService.registrarPago(any(Pago.class))).thenReturn(pago);

        mockMvc.perform(post("/api/v0/pagos/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pago)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    public void testCrearPago_DatosInvalidos() throws Exception {
        Pago pagoInvalido = new Pago();
        pagoInvalido.setMonto(-100.0); // monto invalido, viola @Positive
        // socioId tambien queda null, viola @NotNull

        mockMvc.perform(post("/api/v0/pagos/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pagoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testActualizarPago() throws Exception {
        when(pagoService.actualizarPago(eq(1L), any(Pago.class))).thenReturn(pago);

        mockMvc.perform(put("/api/v0/pagos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pago)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testEliminarPago() throws Exception {
        doNothing().when(pagoService).borrarPago(1L);

        mockMvc.perform(delete("/api/v0/pagos/borrar/1"))
                .andExpect(status().isOk());

        verify(pagoService, times(1)).borrarPago(1L);
    }

    @Test
    public void testBuscarPorSocio() throws Exception {
        when(pagoService.buscarPorSocioId(2L)).thenReturn(List.of(pago));

        mockMvc.perform(get("/api/v0/pagos/socio/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].socioId").value(2));
    }
}
