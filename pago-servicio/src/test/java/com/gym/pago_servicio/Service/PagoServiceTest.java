package com.gym.pago_servicio.Service;

import com.gym.pago_servicio.model.Pago;
import com.gym.pago_servicio.Repository.PagoRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@ActiveProfiles("test")
public class PagoServiceTest {

    @Autowired
    private PagoService pagoService;

    @MockitoBean
    private PagoRepository pagoRepository;

    @Test
    public void testObtenerTodas() {
        Pago pago = crearPago();
        when(pagoRepository.findAll()).thenReturn(List.of(pago));

        List<Pago> pagos = pagoService.obtenerTodas();
        assertNotNull(pagos);
        assertEquals(1, pagos.size());
        assertEquals(pago.getId(), pagos.get(0).getId());
    }

    @Test
    public void testBuscarPorId() {
        Long id = 1L;
        Pago pago = crearPago();
        when(pagoRepository.findById(id)).thenReturn(Optional.of(pago));

        Optional<Pago> encontrado = pagoService.buscarPorId(id);
        assertTrue(encontrado.isPresent());
        assertEquals(id, encontrado.get().getId());
    }

    @Test
    public void testBuscarPorId_NoExiste() {
        Long id = 99L;
        when(pagoRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Pago> encontrado = pagoService.buscarPorId(id);
        assertFalse(encontrado.isPresent());
    }

    @Test
    public void testRegistrarPago() {
        Pago pago = crearPagoSinFecha();
        when(pagoRepository.save(any(Pago.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pago guardado = pagoService.registrarPago(pago);
        assertNotNull(guardado);
        assertNotNull(guardado.getFechaPago());
        assertEquals("PENDIENTE", guardado.getEstado());
        verify(pagoRepository, times(1)).save(pago);
    }

    @Test
    public void testRegistrarPago_ConEstadoExistente() {
        Pago pago = crearPagoSinFecha();
        pago.setEstado("PAGADO");
        when(pagoRepository.save(any(Pago.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pago guardado = pagoService.registrarPago(pago);
        assertEquals("PAGADO", guardado.getEstado());
    }

    @Test
    public void testActualizarPago() {
        Long id = 1L;
        Pago existente = crearPago();
        Pago actualizado = crearPago();
        actualizado.setMonto(20000.0);
        actualizado.setMetodoPago("Transferencia");
        actualizado.setEstado("PAGADO");

        when(pagoRepository.findById(id)).thenReturn(Optional.of(existente));
        when(pagoRepository.save(any(Pago.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pago resultado = pagoService.actualizarPago(id, actualizado);
        assertNotNull(resultado);
        assertEquals(20000.0, resultado.getMonto());
        assertEquals("Transferencia", resultado.getMetodoPago());
        assertEquals("PAGADO", resultado.getEstado());
    }

    @Test
    public void testActualizarPago_NoExiste() {
        Long id = 99L;
        Pago actualizado = crearPago();
        when(pagoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(jakarta.persistence.EntityNotFoundException.class, () -> {
            pagoService.actualizarPago(id, actualizado);
        });
    }

    @Test
    public void testBorrarPago() {
        Long id = 1L;
        doNothing().when(pagoRepository).deleteById(id);

        pagoService.borrarPago(id);
        verify(pagoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testBuscarPorSocioId() {
        Long socioId = 2L;
        Pago pago = crearPago();
        when(pagoRepository.findBySocioId(socioId)).thenReturn(List.of(pago));

        List<Pago> resultado = pagoService.buscarPorSocioId(socioId);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(socioId, resultado.get(0).getSocioId());
    }

    private Pago crearPago() {
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setSocioId(2L);
        pago.setMonto(15000.0);
        pago.setMetodoPago("Visa");
        pago.setEstado("PENDIENTE");
        pago.setFechaPago(LocalDateTime.now());
        return pago;
    }

    private Pago crearPagoSinFecha() {
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setSocioId(2L);
        pago.setMonto(15000.0);
        pago.setMetodoPago("Visa");
        return pago;
    }
}
