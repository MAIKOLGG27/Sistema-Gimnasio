package com.gym.contrato_servicio.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;

import com.gym.contrato_servicio.model.ContratoModel;
import com.gym.contrato_servicio.service.ContratoService;
import com.gym.contrato_servicio.Assembler.AssemblerContrato;

@WebMvcTest(ContratoController.class)
public class ContratoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContratoService servicio;

    @MockBean
    private AssemblerContrato assembler;

    @Test
    void obtenerPorId_DebeRetornarStatus200_Y_Datos() throws Exception {
        ContratoModel contrato = new ContratoModel();
        contrato.setId(1L);
        contrato.setEstado("ACTIVO");

        when(servicio.obtenerPorId(1L)).thenReturn(contrato);
        when(assembler.toModel(contrato)).thenReturn(EntityModel.of(contrato));

        mockMvc.perform(get("/contratos/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.estado").value("ACTIVO"))
               .andExpect(jsonPath("$.id").value(1));
    }
}