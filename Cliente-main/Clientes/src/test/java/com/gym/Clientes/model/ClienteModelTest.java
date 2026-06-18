package com.gym.Clientes.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteModelTest {

    @Test
    void testCrearClienteConDatos() {
        cliente cli = new cliente();
        cli.setId(1L);
        cli.setRut("12345678-9");
        cli.setNombre("Juan Perez");
        cli.setPassword("1234");
        cli.setCorreo("juan@gmail.com");
        cli.setTelefono(912345678);

        assertEquals(1L, cli.getId());
        assertEquals("12345678-9", cli.getRut());
        assertEquals("Juan Perez", cli.getNombre());
        assertEquals("1234", cli.getPassword());
        assertEquals("juan@gmail.com", cli.getCorreo());
        assertEquals(912345678, cli.getTelefono());
    }

    @Test
    void testConstructorCompleto() {
        cliente cli = new cliente(1L, "12345678-9", "Juan Perez", "1234", "juan@gmail.com", 912345678);

        assertNotNull(cli);
        assertEquals("Juan Perez", cli.getNombre());
    }

    @Test
    void testConstructorVacio() {
        cliente cli = new cliente();
        assertNotNull(cli);
        assertNull(cli.getId());
        assertNull(cli.getNombre());
    }
}