package com.gym.contrato_servicio.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
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
        contratoMock = new ContratoModel();
        contratoMock.setId(1L);
        contratoMock.setUsuarioId(10L);
        contratoMock.setMebresiaId(5L);
        contratoMock.setEstado("ACTIVO");
        contratoMock.setFechaInicio(LocalDate.now());
    }

    @Test
    void obtenerPorId_DebeRetornarContrato_CuandoExiste() {
        when(repo.findById(1L)).thenReturn(Optional.of(contratoMock));

        ContratoModel resultado = service.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("ACTIVO", resultado.getEstado());
        assertEquals(10L, resultado.getUsuarioId());
        verify(repo, times(1)).findById(1L);
    }
}