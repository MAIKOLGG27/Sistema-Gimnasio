package com.gym.progreso_servicio.service;

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
    }

    @Test
    void obtenerTodos_DebeRetornarListaDeProgresos() {
        when(progresoRepository.findAll()).thenReturn(Arrays.asList(progresoMock));
        List<Progreso> resultado = progresoService.obtenerTodos();
        assertEquals(1, resultado.size());
    }

    @Test
    void obtenerPorClienteId_DebeRetornarListaDelCliente() {
        when(progresoRepository.findByClienteId(105L)).thenReturn(Arrays.asList(progresoMock));
        List<Progreso> resultado = progresoService.obtenerPorClienteId(105L);
        assertEquals(1, resultado.size());
        assertEquals(105L, resultado.get(0).getClienteId());
    }

    @Test
    void crearProgreso_DebeAsignarFechaActualSiEsNula() {
        Progreso nuevo = new Progreso(null, 105L, 80.0, 20.0, "", null);
        when(progresoRepository.save(any(Progreso.class))).thenReturn(nuevo);
        
        progresoService.crearProgreso(nuevo);
        assertNotNull(nuevo.getFecha()); // Verifica que el servicio le puso la fecha
        verify(progresoRepository, times(1)).save(nuevo);
    }

    @Test
    void eliminarProgreso_DebeLlamarAlRepositorio() {
        doNothing().when(progresoRepository).deleteById(1L);
        progresoService.eliminarProgreso(1L);
        verify(progresoRepository, times(1)).deleteById(1L);
    }
}