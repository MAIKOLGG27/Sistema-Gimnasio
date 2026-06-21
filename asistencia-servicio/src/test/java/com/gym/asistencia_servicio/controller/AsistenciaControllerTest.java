package com.gym.asistencia_servicio.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.gym.asistencia_servicio.model.AsistenciaModel;
import com.gym.asistencia_servicio.service.AsistenciaService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

@WebMvcTest(AsistenciaController.class)
public class AsistenciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AsistenciaService asistenciaService;

    @Autowired
    private ObjectMapper objectMapper;

    private AsistenciaModel asistencia;

    @BeforeEach
    void setUp() {
        asistencia = new AsistenciaModel();
        asistencia.setId(1L);
        asistencia.setUsuarioId(10L);
        asistencia.setFechaHoraEntrada(LocalDateTime.now());
    }

    @Test
    public void testTraerTodos() throws Exception {
        when(asistenciaService.obtenerTodas()).thenReturn(List.of(asistencia));

        mockMvc.perform(get("/asistencia/Listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].usuarioId").value(10));
    }

    @Test
    public void testBuscarPorId() throws Exception {
        when(asistenciaService.buscarPorId(1L)).thenReturn(asistencia);

        mockMvc.perform(get("/asistencia/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.usuarioId").value(10));
    }

    @Test
    public void testBuscarPorId_NoExiste() throws Exception {
        when(asistenciaService.buscarPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/asistencia/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRegistrarAsistencia() throws Exception {
        when(asistenciaService.registrarAsistencia(any(AsistenciaModel.class))).thenReturn(asistencia);

        mockMvc.perform(post("/asistencia/Registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(asistencia)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.usuarioId").value(10));
    }

    @Test
    public void testActualizarAsistencia() throws Exception {
        when(asistenciaService.actualizarAsistencia(eq(1L), any(AsistenciaModel.class))).thenReturn(asistencia);

        mockMvc.perform(put("/asistencia/Actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(asistencia)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testActualizarAsistencia_NoExiste() throws Exception {
        when(asistenciaService.actualizarAsistencia(eq(99L), any(AsistenciaModel.class))).thenReturn(null);

        mockMvc.perform(put("/asistencia/Actualizar/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(asistencia)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testEliminarAsistencia() throws Exception {
        doNothing().when(asistenciaService).borrarAsistencia(1L);

        mockMvc.perform(delete("/asistencia/Eliminar/1"))
                .andExpect(status().isOk());

        verify(asistenciaService, times(1)).borrarAsistencia(1L);
    }
}
