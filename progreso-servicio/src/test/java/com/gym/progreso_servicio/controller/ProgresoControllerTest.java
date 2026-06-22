package com.gym.progreso_servicio.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.gym.progreso_servicio.model.Progreso;
import com.gym.progreso_servicio.service.ProgresoService;
import com.gym.progreso_servicio.Assembler.AssemblerProgreso;

@WebMvcTest(ProgresoController.class)
public class ProgresoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProgresoService progresoService;

    @MockBean
    private AssemblerProgreso assembler;

    @Test
    void obtenerPorId_DebeRetornarStatus200() throws Exception {
        Progreso progreso = new Progreso();
        progreso.setId(1L);
        progreso.setPeso(75.5);

        when(progresoService.obtenerPorId(1L)).thenReturn(Optional.of(progreso));
        when(assembler.toModel(progreso)).thenReturn(EntityModel.of(progreso));

        mockMvc.perform(get("/api/progreso/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.peso").value(75.5));
    }

    @Test
    void obtenerTodos_DebeRetornarStatus200() throws Exception {
        Progreso progreso = new Progreso();
        when(progresoService.obtenerTodos()).thenReturn(Arrays.asList(progreso));
        when(assembler.toModel(any())).thenReturn(EntityModel.of(progreso));

        mockMvc.perform(get("/api/progreso"))
               .andExpect(status().isOk());
    }

    @Test
    void crearProgreso_DebeRetornarStatus201() throws Exception {
        Progreso progreso = new Progreso();
        progreso.setPeso(80.0);
        when(progresoService.crearProgreso(any(Progreso.class))).thenReturn(progreso);
        when(assembler.toModel(any())).thenReturn(EntityModel.of(progreso));

        mockMvc.perform(post("/api/progreso")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(progreso)))
               .andExpect(status().isCreated()) // Espera un 201 Created
               .andExpect(jsonPath("$.peso").value(80.0));
    }

    @Test
    void eliminarProgreso_DebeRetornarStatus204() throws Exception {
        doNothing().when(progresoService).eliminarProgreso(1L);
        
        mockMvc.perform(delete("/api/progreso/1"))
               .andExpect(status().isNoContent()); // Espera un 204 No Content
    }
}