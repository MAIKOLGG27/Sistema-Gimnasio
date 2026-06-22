package com.gym.membrecias.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;

import com.gym.membrecias.model.membreciaModel;
import com.gym.membrecias.service.membreciaService;
import com.gym.membrecias.Assembler.AssemblerMembresia;

@WebMvcTest(membreciaController.class) 
public class membreciaControllerTest {

    @Autowired
    private MockMvc mockMvc; 

    @MockBean
    private membreciaService servicio; 
    @MockBean
    private AssemblerMembresia assembler; 
    @Test
    void obtenerPorId_DebeRetornarStatus200_Y_Datos() throws Exception {
        
        membreciaModel membresia = new membreciaModel();
        membresia.setId(1L);
        membresia.setNombre("Membresía Básica");

        
        when(servicio.obtenerPorId(1L)).thenReturn(membresia);
        
        
        when(assembler.toModel(membresia)).thenReturn(EntityModel.of(membresia));

        
        mockMvc.perform(get("/membresias/1"))
               .andExpect(status().isOk()) 
               .andExpect(jsonPath("$.nombre").value("Membresía Básica")) 
               .andExpect(jsonPath("$.id").value(1));
    }
}