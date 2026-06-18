package com.gym.Clientes.repository;

import com.gym.Clientes.model.cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    private cliente clienteGuardado;

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
        cliente cli = new cliente(null, "12345678-9", "Juan Perez", "1234", "juan@gmail.com", 912345678);
        clienteGuardado = clienteRepository.save(cli);
    }

    @Test
    void testGuardarCliente() {
        assertNotNull(clienteGuardado.getId());
        assertEquals("Juan Perez", clienteGuardado.getNombre());
    }

    @Test
    void testBuscarPorId() {
        Optional<cliente> resultado = clienteRepository.findById(clienteGuardado.getId());
        assertTrue(resultado.isPresent());
        assertEquals("juan@gmail.com", resultado.get().getCorreo());
    }

    @Test
    void testBuscarTodos() {
        List<cliente> lista = clienteRepository.findAll();
        assertFalse(lista.isEmpty());
    }

    @Test
    void testBuscarPorNombre() {
        Optional<cliente> resultado = clienteRepository.findByNombre("Juan Perez");
        assertTrue(resultado.isPresent());
        assertEquals("12345678-9", resultado.get().getRut());
    }

    @Test
    void testBuscarPorNombreNoExiste() {
        Optional<cliente> resultado = clienteRepository.findByNombre("No Existe");
        assertFalse(resultado.isPresent());
    }

    @Test
    void testEliminarCliente() {
        clienteRepository.deleteById(clienteGuardado.getId());
        Optional<cliente> resultado = clienteRepository.findById(clienteGuardado.getId());
        assertFalse(resultado.isPresent());
    }
}