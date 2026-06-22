package com.gym.contrato_servicio.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gym.contrato_servicio.model.ContratoModel;
import com.gym.contrato_servicio.repository.ContratoRepository;

@ExtendWith(MockitoExtension.class)
public class ContratoServiceTest {

    @Mock
    private ContratoRepository repo;

    @InjectMocks
    private ContratoService service;

    private ContratoModel contratoMock;

    @BeforeEach
    void setUp() {
        contratoMock = new ContratoModel(1L, 10L, 5L, LocalDate.now(), LocalDate.now().plusMonths(1), "ACTIVO");
    }

    @Test
    void obtenerPorId_DebeRetornarContrato_CuandoExiste() {
        when(repo.findById(1L)).thenReturn(Optional.of(contratoMock));
        ContratoModel resultado = service.obtenerPorId(1L);
        assertNotNull(resultado);
        assertEquals("ACTIVO", resultado.getEstado());
    }

    @Test
    void obtenerPorId_DebeLanzarExcepcion_CuandoNoExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> service.obtenerPorId(99L));
        assertTrue(exception.getMessage().contains("no existe"));
    }

    @Test
    void obtenerTodas_DebeRetornarListaDeContratos() {
        when(repo.findAll()).thenReturn(Arrays.asList(contratoMock, new ContratoModel()));
        List<ContratoModel> resultado = service.obtenerTodas();
        assertEquals(2, resultado.size());
    }

    @Test
    void registrarContrato_DebeGuardarYRetornarContrato() {
        when(repo.save(any(ContratoModel.class))).thenReturn(contratoMock);
        ContratoModel resultado = service.registrarContrato(contratoMock);
        assertNotNull(resultado);
        assertEquals(10L, resultado.getUsuarioId());
    }

    @Test
    void actualizarContrato_DebeActualizar_CuandoExiste() {
        ContratoModel nuevosDatos = new ContratoModel(1L, 10L, 5L, LocalDate.now(), LocalDate.now().plusMonths(2), "INACTIVO");
        when(repo.findById(1L)).thenReturn(Optional.of(contratoMock));
        when(repo.save(any(ContratoModel.class))).thenReturn(nuevosDatos);
        
        ContratoModel resultado = service.actualizarContrato(1L, nuevosDatos);
        assertEquals("INACTIVO", resultado.getEstado());
    }

    @Test
    void borrarContrato_DebeLlamarAlRepositorio_CuandoExiste() {
        when(repo.existsById(1L)).thenReturn(true);
        doNothing().when(repo).deleteById(1L);
        
        service.borrarContrato(1L);
        verify(repo, times(1)).deleteById(1L);
    }
}