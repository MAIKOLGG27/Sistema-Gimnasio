package com.gym.contrato_servicio.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.gym.contrato_servicio.model.ContratoModel;
import com.gym.contrato_servicio.service.ContratoService;
import com.gym.contrato_servicio.Assembler.AssemblerContrato;

@WebMvcTest(ContratoController.class)
public class ContratoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; 

    @MockBean
    private ContratoService servicio;

    @MockBean
    private AssemblerContrato assembler;

    @Test
    void obtenerPorId_DebeRetornarStatus200() throws Exception {
        ContratoModel contrato = new ContratoModel();
        contrato.setId(1L);
        contrato.setEstado("ACTIVO");

        when(servicio.obtenerPorId(1L)).thenReturn(contrato);
        when(assembler.toModel(contrato)).thenReturn(EntityModel.of(contrato));

        mockMvc.perform(get("/contratos/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.estado").value("ACTIVO"));
    }

    @Test
    void traerTodos_DebeRetornarListaYStatus200() throws Exception {
        ContratoModel contrato = new ContratoModel();
        when(servicio.obtenerTodas()).thenReturn(Arrays.asList(contrato));
        when(assembler.toModel(any())).thenReturn(EntityModel.of(contrato));

        mockMvc.perform(get("/contratos/Listar"))
               .andExpect(status().isOk());
    }

    @Test
    void crearContrato_DebeRetornarStatus200() throws Exception {
        ContratoModel contrato = new ContratoModel();
        contrato.setEstado("NUEVO");
        when(servicio.registrarContrato(any(ContratoModel.class))).thenReturn(contrato);

        mockMvc.perform(post("/contratos/crear")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(contrato)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.estado").value("NUEVO"));
    }

    @Test
    void eliminarContrato_DebeRetornarStatus200() throws Exception {
        doNothing().when(servicio).borrarContrato(1L);
        mockMvc.perform(delete("/contratos/borrar/1"))
               .andExpect(status().isOk());
    }
}