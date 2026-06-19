package com.gym.registro_servicio.Service;

import com.gym.registro_servicio.Model.Registro;
import com.gym.registro_servicio.Repository.RegistroRepository;
import com.gym.registro_servicio.webPago.PagoPag;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class RegistroServiceTest {

    @Autowired
    private RegistroService registroService;

    @MockitoBean
    private RegistroRepository registroRepository;

    @MockitoBean
    private PagoPag pagoPag;

    @Test
    public void testObtenerTodas() {
        Registro registro = crearRegistro();
        when(registroRepository.findAll()).thenReturn(List.of(registro));

        List<Registro> registros = registroService.obtenerTodas();
        assertNotNull(registros);
        assertEquals(1, registros.size());
        assertEquals(registro.getId(), registros.get(0).getId());
    }

    @Test
    public void testBuscarPorId() {
        Long id = 1L;
        Registro registro = crearRegistro();
        when(registroRepository.findById(id)).thenReturn(Optional.of(registro));

        Optional<Registro> encontrado = registroService.buscarPorId(id);
        assertTrue(encontrado.isPresent());
        assertEquals(id, encontrado.get().getId());
    }

    @Test
    public void testBuscarPorId_NoExiste() {
        Long id = 99L;
        when(registroRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Registro> encontrado = registroService.buscarPorId(id);
        assertFalse(encontrado.isPresent());
    }

    @Test
    public void testRegistrarRegistro() {
        Registro registro = crearRegistro();
        when(registroRepository.save(any(Registro.class))).thenReturn(registro);

        Registro guardado = registroService.registrarRegistro(registro);
        assertNotNull(guardado);
        assertEquals(registro.getId(), guardado.getId());
        verify(registroRepository, times(1)).save(registro);
    }

    @Test
    public void testActualizarRegistro() {
        Long id = 1L;
        Registro existente = crearRegistro();
        Registro actualizado = crearRegistro();
        actualizado.setNombre("Pedro");
        actualizado.setApellido("Soto");
        actualizado.setEmail("pedro.soto@email.com");
        actualizado.setTelefono("+56999999999");
        actualizado.setActivo(false);

        when(registroRepository.findById(id)).thenReturn(Optional.of(existente));
        when(registroRepository.save(any(Registro.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Registro resultado = registroService.actualizarRegistro(id, actualizado);
        assertNotNull(resultado);
        assertEquals("Pedro", resultado.getNombre());
        assertEquals("Soto", resultado.getApellido());
        assertEquals("pedro.soto@email.com", resultado.getEmail());
        assertFalse(resultado.getActivo());
    }

    @Test
    public void testActualizarRegistro_NoExiste() {
        Long id = 99L;
        Registro actualizado = crearRegistro();
        when(registroRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            registroService.actualizarRegistro(id, actualizado);
        });
    }

    @Test
    public void testBorrarRegistro() {
        Long id = 1L;
        doNothing().when(registroRepository).deleteById(id);

        registroService.borrarRegistro(id);
        verify(registroRepository, times(1)).deleteById(id);
    }

    @Test
    public void testObtenerRegistroConPago() {
        Long id = 1L;
        Registro registro = crearRegistro();
        Map<String, Object> pagoSimulado = Map.of("id", 3L, "estado", "PAGADO");

        when(registroRepository.findById(id)).thenReturn(Optional.of(registro));
        when(pagoPag.obtenerPago(registro.getPagoId())).thenReturn(pagoSimulado);

        Map resultado = registroService.obtenerRegistroConPago(id);
        assertNotNull(resultado);
        assertEquals(registro, resultado.get("registro"));
        assertEquals(pagoSimulado, resultado.get("pago"));
    }

    @Test
    public void testObtenerRegistroConPago_SinPago() {
        Long id = 1L;
        Registro registro = crearRegistro();

        when(registroRepository.findById(id)).thenReturn(Optional.of(registro));
        when(pagoPag.obtenerPago(registro.getPagoId())).thenReturn(null);

        Map resultado = registroService.obtenerRegistroConPago(id);
        assertEquals("Sin pago", resultado.get("pago"));
    }

    @Test
    public void testObtenerRegistroConPago_RegistroNoExiste() {
        Long id = 99L;
        when(registroRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            registroService.obtenerRegistroConPago(id);
        });
    }

    private Registro crearRegistro() {
        Registro registro = new Registro();
        registro.setId(1L);
        registro.setPagoId(3L);
        registro.setNombre("Juan");
        registro.setApellido("Pérez");
        registro.setEmail("juan.perez@email.com");
        registro.setTelefono("+56912345678");
        registro.setFechaRegistro(LocalDate.now());
        registro.setActivo(true);
        return registro;
    }
}
