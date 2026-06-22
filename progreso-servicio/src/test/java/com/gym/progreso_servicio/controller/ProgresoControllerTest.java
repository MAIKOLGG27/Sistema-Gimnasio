package com.gym.progreso_servicio.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;

import com.gym.progreso_servicio.model.Progreso;
import com.gym.progreso_servicio.service.ProgresoService;
import com.gym.progreso_servicio.Assembler.AssemblerProgreso;

@WebMvcTest(ProgresoController.class)
public class ProgresoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProgresoService progresoService;

    @MockBean
    private AssemblerProgreso assembler;

    @Test
    void obtenerPorId_DebeRetornarStatus200_Y_Datos() throws Exception {
        Progreso progreso = new Progreso();
        progreso.setId(1L);
        progreso.setPeso(75.5);

        when(progresoService.obtenerPorId(1L)).thenReturn(Optional.of(progreso));
        when(assembler.toModel(progreso)).thenReturn(EntityModel.of(progreso));

        mockMvc.perform(get("/api/progreso/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.peso").value(75.5))
               .andExpect(jsonPath("$.id").value(1));
    }
}