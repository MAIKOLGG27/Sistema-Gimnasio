package com.gym.Clientes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.Clientes.Config.SecurityConfig;
import com.gym.Clientes.assemblers.ClienteAssembler;
import com.gym.Clientes.model.cliente;
import com.gym.Clientes.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(ClienteController.class)
@Import(SecurityConfig.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteService;

    @MockitoBean
    private ClienteAssembler clienteAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    private cliente cli;

    @BeforeEach
    void setUp() {
        cli = new cliente(1L, "12345678-9", "Juan Perez", "1234", "juan@gmail.com", 912345678);
    }

    private EntityModel<cliente> mockModel(cliente c) {
        return EntityModel.of(c,
            linkTo(methodOn(ClienteController.class).obtenerPorId(c.getId())).withSelfRel());
    }

    // ── GET todos ─────────────────────────────────────────────────────────
    @Test
    @WithMockUser(username = "Maty", roles = "ADMIN")
    void testObtenerTodos_200() throws Exception {
        when(clienteService.obtenerTodos()).thenReturn(List.of(cli));
        when(clienteAssembler.toModel(cli)).thenReturn(mockModel(cli));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk());
    }

    // ── GET por id ────────────────────────────────────────────────────────
    @Test
    @WithMockUser(username = "Maty", roles = "ADMIN")
    void testObtenerPorId_200() throws Exception {
        when(clienteService.obtenerPorId(1L)).thenReturn(Optional.of(cli));
        when(clienteAssembler.toModel(cli)).thenReturn(mockModel(cli));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Maty", roles = "ADMIN")
    void testObtenerPorId_404() throws Exception {
        when(clienteService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    // ── POST ──────────────────────────────────────────────────────────────
    @Test
    @WithMockUser(username = "Maty", roles = "ADMIN")
    void testCrearCliente_201() throws Exception {
        when(clienteService.crearCliente(any(cliente.class))).thenReturn(cli);
        when(clienteAssembler.toModel(cli)).thenReturn(mockModel(cli));

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cli)))
                .andExpect(status().isCreated());
    }

    // ── PUT ───────────────────────────────────────────────────────────────
    @Test
    @WithMockUser(username = "Maty", roles = "ADMIN")
    void testActualizarCliente_200() throws Exception {
        when(clienteService.actualizarCliente(eq(1L), any(cliente.class))).thenReturn(Optional.of(cli));
        when(clienteAssembler.toModel(cli)).thenReturn(mockModel(cli));

        mockMvc.perform(put("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cli)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Maty", roles = "ADMIN")
    void testActualizarCliente_404() throws Exception {
        when(clienteService.actualizarCliente(eq(99L), any(cliente.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/usuarios/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cli)))
                .andExpect(status().isNotFound());
    }

    // ── DELETE ────────────────────────────────────────────────────────────
    @Test
    @WithMockUser(username = "Maty", roles = "ADMIN")
    void testEliminarCliente_204() throws Exception {
        when(clienteService.obtenerPorId(1L)).thenReturn(Optional.of(cli));

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "Maty", roles = "ADMIN")
    void testEliminarCliente_404() throws Exception {
        when(clienteService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    
}