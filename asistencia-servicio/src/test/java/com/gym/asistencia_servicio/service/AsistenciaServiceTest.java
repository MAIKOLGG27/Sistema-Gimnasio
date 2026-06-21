package com.gym.asistencia_servicio.service;

import com.gym.asistencia_servicio.model.AsistenciaModel;
import com.gym.asistencia_servicio.repository.AsistenciaRepository;

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
public class AsistenciaServiceTest {

    @Autowired
    private AsistenciaService asistenciaService;

    @MockitoBean
    private AsistenciaRepository asistenciaRepository;

    @Test
    public void testObtenerTodas() {
        AsistenciaModel asistencia = crearAsistencia();
        when(asistenciaRepository.findAll()).thenReturn(List.of(asistencia));

        List<AsistenciaModel> asistencias = asistenciaService.obtenerTodas();
        assertNotNull(asistencias);
        assertEquals(1, asistencias.size());
        assertEquals(asistencia.getId(), asistencias.get(0).getId());
    }

    @Test
    public void testBuscarPorId() {
        Long id = 1L;
        AsistenciaModel asistencia = crearAsistencia();
        when(asistenciaRepository.findById(id)).thenReturn(Optional.of(asistencia));

        AsistenciaModel encontrada = asistenciaService.buscarPorId(id);
        assertNotNull(encontrada);
        assertEquals(id.longValue(), encontrada.getId());
    }

    @Test
    public void testBuscarPorId_NoExiste() {
        Long id = 99L;
        when(asistenciaRepository.findById(id)).thenReturn(Optional.empty());

        AsistenciaModel encontrada = asistenciaService.buscarPorId(id);
        assertNull(encontrada);
    }

    @Test
    public void testRegistrarAsistencia() {
        AsistenciaModel asistencia = crearAsistencia();
        when(asistenciaRepository.save(any(AsistenciaModel.class))).thenReturn(asistencia);

        AsistenciaModel guardada = asistenciaService.registrarAsistencia(asistencia);
        assertNotNull(guardada);
        assertEquals(asistencia.getId(), guardada.getId());
        verify(asistenciaRepository, times(1)).save(asistencia);
    }

    @Test
    public void testActualizarAsistencia() {
        Long id = 1L;
        AsistenciaModel actualizado = crearAsistencia();
        actualizado.setUsuarioId(20L);

        when(asistenciaRepository.existsById(id)).thenReturn(true);
        when(asistenciaRepository.save(any(AsistenciaModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AsistenciaModel resultado = asistenciaService.actualizarAsistencia(id, actualizado);
        assertNotNull(resultado);
        assertEquals(Long.valueOf(20L), resultado.getUsuarioId());
    }

    @Test
    public void testActualizarAsistencia_NoExiste() {
        Long id = 99L;
        AsistenciaModel actualizado = crearAsistencia();
        when(asistenciaRepository.existsById(id)).thenReturn(false);

        AsistenciaModel resultado = asistenciaService.actualizarAsistencia(id, actualizado);
        assertNull(resultado);
    }

    @Test
    public void testBorrarAsistencia() {
        Long id = 1L;
        doNothing().when(asistenciaRepository).deleteById(id);

        asistenciaService.borrarAsistencia(id);
        verify(asistenciaRepository, times(1)).deleteById(id);
    }

    private AsistenciaModel crearAsistencia() {
        AsistenciaModel asistencia = new AsistenciaModel();
        asistencia.setId(1L);
        asistencia.setUsuarioId(10L);
        asistencia.setFechaHoraEntrada(LocalDateTime.now());
        return asistencia;
    }
}
