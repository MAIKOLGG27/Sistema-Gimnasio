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

@WebMvcTest(membreciaController.class) // Solo carga el contexto web para este controlador
public class membreciaControllerTest {

    @Autowired
    private MockMvc mockMvc; // Objeto para simular peticiones HTTP (como Postman pero en código)

    @MockBean
    private membreciaService servicio; // Simulamos el servicio

    @MockBean
    private AssemblerMembresia assembler; // Simulamos el Assembler HATEOAS

    @Test
    void obtenerPorId_DebeRetornarStatus200_Y_Datos() throws Exception {
        // 1. Arrange: Preparamos los datos de prueba
        membreciaModel membresia = new membreciaModel();
        membresia.setId(1L);
        membresia.setNombre("Membresía Básica");

        // Simulamos la respuesta del servicio
        when(servicio.obtenerPorId(1L)).thenReturn(membresia);
        
        // Simulamos la respuesta del Assembler (sin los links complejos para simplificar el test)
        when(assembler.toModel(membresia)).thenReturn(EntityModel.of(membresia));

        // 2 & 3. Act & Assert: Simulamos una petición HTTP GET a /membresias/1
        mockMvc.perform(get("/membresias/1"))
               .andExpect(status().isOk()) // Esperamos un HTTP 200 OK
               .andExpect(jsonPath("$.nombre").value("Membresía Básica")) // Validamos el JSON
               .andExpect(jsonPath("$.id").value(1));
    }
}