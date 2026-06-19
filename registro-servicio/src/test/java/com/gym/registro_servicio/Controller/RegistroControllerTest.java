package com.gym.registro_servicio.Controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.gym.registro_servicio.Model.Registro;
import com.gym.registro_servicio.Service.RegistroService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@WebMvcTest(RegistroController.class)
public class RegistroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegistroService registroService;

    @Autowired
    private ObjectMapper objectMapper;

    private Registro registro;

    @BeforeEach
    void setUp() {
        registro = new Registro();
        registro.setId(1L);
        registro.setPagoId(3L);
        registro.setNombre("Juan");
        registro.setApellido("Pérez");
        registro.setEmail("juan.perez@email.com");
        registro.setTelefono("+56912345678");
        registro.setFechaRegistro(LocalDate.now());
        registro.setActivo(true);
    }

    @Test
    public void testTraerTodos() throws Exception {
        when(registroService.obtenerTodas()).thenReturn(List.of(registro));

        mockMvc.perform(get("/api/v0/registros/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    public void testBuscarPorId() throws Exception {
        when(registroService.buscarPorId(1L)).thenReturn(Optional.of(registro));

        mockMvc.perform(get("/api/v0/registros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("juan.perez@email.com"));
    }

    @Test
    public void testBuscarPorId_NoExiste() throws Exception {
        when(registroService.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v0/registros/99"))
                .andExpect(status().isOk())
                .andExpect(content().string("null"));
    }

    @Test
    public void testCrearRegistro() throws Exception {
        when(registroService.registrarRegistro(any(Registro.class))).thenReturn(registro);

        mockMvc.perform(post("/api/v0/registros/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    public void testActualizarRegistro() throws Exception {
        when(registroService.actualizarRegistro(eq(1L), any(Registro.class))).thenReturn(registro);

        mockMvc.perform(put("/api/v0/registros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testEliminarRegistro() throws Exception {
        doNothing().when(registroService).borrarRegistro(1L);

        mockMvc.perform(delete("/api/v0/registros/borrar/1"))
                .andExpect(status().isOk());

        verify(registroService, times(1)).borrarRegistro(1L);
    }

    @Test
    public void testBuscarConPago() throws Exception {
        Map<String, Object> respuesta = Map.of(
                "registro", registro,
                "pago", Map.of("id", 3L, "estado", "PAGADO")
        );
        when(registroService.obtenerRegistroConPago(1L)).thenReturn(respuesta);

        mockMvc.perform(get("/api/v0/registros/conPago/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registro.id").value(1))
                .andExpect(jsonPath("$.pago.estado").value("PAGADO"));
    }
}
