package com.gym.Clientes.service;

import com.gym.Clientes.model.cliente;
import com.gym.Clientes.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private cliente cli;

    @BeforeEach
    void setUp() {
        cli = new cliente(1L, "12345678-9", "Juan Perez", "1234", "juan@gmail.com", 912345678);
    }

    @Test
    void testObtenerTodos() {
        when(clienteRepository.findAll()).thenReturn(List.of(cli));

        List<cliente> resultado = clienteService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("Juan Perez", resultado.get(0).getNombre());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorIdExiste() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cli));

        Optional<cliente> resultado = clienteService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("juan@gmail.com", resultado.get().getCorreo());
    }

    @Test
    void testObtenerPorIdNoExiste() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<cliente> resultado = clienteService.obtenerPorId(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void testCrearCliente() {
        when(clienteRepository.save(cli)).thenReturn(cli);

        cliente nuevo = clienteService.crearCliente(cli);

        assertNotNull(nuevo);
        assertEquals("Juan Perez", nuevo.getNombre());
        verify(clienteRepository, times(1)).save(cli);
    }

    @Test
    void testActualizarClienteExiste() {
        cliente datosNuevos = new cliente(null, "98765432-1", "Pedro Lopez", "5678", "pedro@gmail.com", 987654321);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cli));
        when(clienteRepository.save(any(cliente.class))).thenReturn(cli);

        Optional<cliente> resultado = clienteService.actualizarCliente(1L, datosNuevos);

        assertTrue(resultado.isPresent());
        verify(clienteRepository, times(1)).save(any(cliente.class));
    }

    @Test
    void testActualizarClienteNoExiste() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<cliente> resultado = clienteService.actualizarCliente(99L, cli);

        assertFalse(resultado.isPresent());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void testEliminarCliente() {
        doNothing().when(clienteRepository).deleteById(1L);

        clienteService.eliminarCliente(1L);

        verify(clienteRepository, times(1)).deleteById(1L);
    }

    
}