package com.gym.progreso_servicio.service;

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

import com.gym.progreso_servicio.model.Progreso;
import com.gym.progreso_servicio.repository.ProgresoRepository;

@ExtendWith(MockitoExtension.class)
public class ProgresoServiceTest {

    @Mock
    private ProgresoRepository progresoRepository;

    @InjectMocks
    private ProgresoService progresoService;

    private Progreso progresoMock;

    @BeforeEach
    void setUp() {
        progresoMock = new Progreso(1L, 105L, 75.5, 18.5, "Todo bien", LocalDate.now());
    }

    @Test
    void obtenerPorId_DebeRetornarProgreso_CuandoExiste() {
        when(progresoRepository.findById(1L)).thenReturn(Optional.of(progresoMock));

        Optional<Progreso> resultado = progresoService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(75.5, resultado.get().getPeso());
        verify(progresoRepository, times(1)).findById(1L);
    }
}