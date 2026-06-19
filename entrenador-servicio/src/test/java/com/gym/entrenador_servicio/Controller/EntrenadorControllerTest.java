package com.gym.entrenador_servicio.Controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.gym.entrenador_servicio.Model.Entrenador;
import com.gym.entrenador_servicio.Service.EntrenadorService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;


@WebMvcTest(EntrenadorController.class)
public class EntrenadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EntrenadorService entrenadorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Entrenador entrenador;

    @BeforeEach
    void setUp() {
        entrenador = new Entrenador();
        entrenador.setId(1L);
        entrenador.setNombre("Carlos");
        entrenador.setApellido("González");
        entrenador.setEspecialidad("CrossFit");
        entrenador.setTelefono("+56987654321");
        entrenador.setEmail("carlos.gonzalez@gym.com");
        entrenador.setActivo(true);
    }

    @Test
    public void testTraerTodos() throws Exception {
        when(entrenadorService.obtenerTodas()).thenReturn(List.of(entrenador));

        // Ojo: la ruta del controller real es "/Listar" con mayuscula, no "/listar".
        mockMvc.perform(get("/api/v0/entrenadores/Listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Carlos"));
    }

    @Test
    public void testBuscarPorId() throws Exception {
        when(entrenadorService.buscarPorId(1L)).thenReturn(Optional.of(entrenador));

        mockMvc.perform(get("/api/v0/entrenadores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.especialidad").value("CrossFit"));
    }

    @Test
    public void testBuscarPorId_NoExiste() throws Exception {
        when(entrenadorService.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v0/entrenadores/99"))
                .andExpect(status().isOk())
                .andExpect(content().string("null"));
    }

    @Test
    public void testCrearEntrenador() throws Exception {
        when(entrenadorService.registrarEntrenador(any(Entrenador.class))).thenReturn(entrenador);

        mockMvc.perform(post("/api/v0/entrenadores/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrenador)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Carlos"));
    }

    @Test
    public void testActualizarEntrenador() throws Exception {
        when(entrenadorService.actualizarEntrenador(eq(1L), any(Entrenador.class))).thenReturn(entrenador);

        mockMvc.perform(put("/api/v0/entrenadores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrenador)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testEliminarEntrenador() throws Exception {
        doNothing().when(entrenadorService).borrarEntrenador(1L);

        mockMvc.perform(delete("/api/v0/entrenadores/borrar/1"))
                .andExpect(status().isOk());

        verify(entrenadorService, times(1)).borrarEntrenador(1L);
    }
}
