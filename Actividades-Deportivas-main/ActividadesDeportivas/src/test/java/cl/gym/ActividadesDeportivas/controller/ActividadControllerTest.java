package cl.gym.ActividadesDeportivas.controller;

import cl.gym.ActividadesDeportivas.assembler.ActividadAssembler;
import cl.gym.ActividadesDeportivas.model.Actividad;
import cl.gym.ActividadesDeportivas.service.ActividadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ActividadController.class)
@Import(ActividadAssembler.class)
@DisplayName("Tests unitarios - ActividadController")
class ActividadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ActividadService actividadService;

    private Actividad buildActividad() {
        return new Actividad(1L, "Yoga", "Clase de yoga para todo nivel");
    }

    private Actividad buildActividadSinId() {
        return new Actividad(null, "Yoga", "Clase de yoga para todo nivel");
    }

    @Test
    @DisplayName("GIVEN: actividades en BD WHEN: GET /api/actividades THEN: 200")
    void shouldReturn200_whenGetAll() throws Exception {
        when(actividadService.obtenerTodos()).thenReturn(List.of(buildActividad()));

        mockMvc.perform(get("/api/actividades"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GIVEN: ID existente WHEN: GET /api/actividades/{id} THEN: 200")
    void shouldReturn200_whenGetById() throws Exception {
        when(actividadService.obtenerPorId(1L)).thenReturn(Optional.of(buildActividad()));

        mockMvc.perform(get("/api/actividades/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GIVEN: ID inexistente WHEN: GET /api/actividades/{id} THEN: 404")
    void shouldReturn404_whenGetByIdNotFound() throws Exception {
        when(actividadService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/actividades/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GIVEN: actividad valida WHEN: POST /api/actividades THEN: 201")
    void shouldReturn201_whenCreate() throws Exception {
        Actividad guardada = buildActividad();
        when(actividadService.guardar(any())).thenReturn(guardada);

        mockMvc.perform(post("/api/actividades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildActividadSinId())))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GIVEN: nombre vacio WHEN: POST /api/actividades THEN: 400")
    void shouldReturn400_whenCreateWithInvalidData() throws Exception {
        Actividad invalida = new Actividad(null, "", "Descripcion valida");

        mockMvc.perform(post("/api/actividades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalida)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GIVEN: ID existente WHEN: PUT /api/actividades/{id} THEN: 200")
    void shouldReturn200_whenUpdate() throws Exception {
        Actividad actualizada = buildActividad();
        when(actividadService.actualizar(eq(1L), any())).thenReturn(Optional.of(actualizada));

        mockMvc.perform(put("/api/actividades/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildActividadSinId())))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GIVEN: ID inexistente WHEN: PUT /api/actividades/{id} THEN: 404")
    void shouldReturn404_whenUpdateNotFound() throws Exception {
        when(actividadService.actualizar(eq(99L), any())).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/actividades/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildActividadSinId())))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GIVEN: ID existente WHEN: DELETE /api/actividades/{id} THEN: 204")
    void shouldReturn204_whenDelete() throws Exception {
        when(actividadService.obtenerPorId(1L)).thenReturn(Optional.of(buildActividad()));

        mockMvc.perform(delete("/api/actividades/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GIVEN: ID inexistente WHEN: DELETE /api/actividades/{id} THEN: 404")
    void shouldReturn404_whenDeleteNotFound() throws Exception {
        when(actividadService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/actividades/99"))
                .andExpect(status().isNotFound());
    }
}