package cl.gym.ActividadesDeportivas.service;

import cl.gym.ActividadesDeportivas.model.Actividad;
import cl.gym.ActividadesDeportivas.repository.ActividadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActividadServiceTest {

    @Mock
    private ActividadRepository actividadRepository;

    @InjectMocks
    private ActividadService actividadService;

    private Actividad actividad;

    @BeforeEach
    void setUp() {
        actividad = new Actividad(1L, "Yoga", "Clase de yoga para todo nivel");
    }

    @Test
    void obtenerTodos_deberiaRetornarListaDeActividades() {
        when(actividadRepository.findAll()).thenReturn(List.of(actividad));

        List<Actividad> resultado = actividadService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("Yoga", resultado.get(0).getNombre());
        verify(actividadRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_deberiaRetornarActividad_cuandoExiste() {
        when(actividadRepository.findById(1L)).thenReturn(Optional.of(actividad));

        Optional<Actividad> resultado = actividadService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Yoga", resultado.get().getNombre());
    }

    @Test
    void obtenerPorId_deberiaRetornarVacio_cuandoNoExiste() {
        when(actividadRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Actividad> resultado = actividadService.obtenerPorId(99L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void guardar_deberiaRetornarActividadGuardada() {
        when(actividadRepository.save(any(Actividad.class))).thenReturn(actividad);

        Actividad resultado = actividadService.guardar(new Actividad(null, "Yoga", "Clase de yoga para todo nivel"));

        assertNotNull(resultado.getIdActividad());
        assertEquals("Yoga", resultado.getNombre());
        verify(actividadRepository, times(1)).save(any(Actividad.class));
    }

    @Test
    void actualizar_deberiaActualizarYRetornarActividad_cuandoExiste() {
        Actividad detalles = new Actividad(null, "Pilates", "Clase de pilates avanzado");
        when(actividadRepository.findById(1L)).thenReturn(Optional.of(actividad));
        when(actividadRepository.save(any(Actividad.class))).thenReturn(actividad);

        Optional<Actividad> resultado = actividadService.actualizar(1L, detalles);

        assertTrue(resultado.isPresent());
        assertEquals("Pilates", resultado.get().getNombre());
        assertEquals("Clase de pilates avanzado", resultado.get().getDescripcion());
        verify(actividadRepository, times(1)).save(actividad);
    }

    @Test
    void actualizar_deberiaRetornarVacio_cuandoNoExiste() {
        Actividad detalles = new Actividad(null, "Pilates", "Clase de pilates avanzado");
        when(actividadRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Actividad> resultado = actividadService.actualizar(99L, detalles);

        assertTrue(resultado.isEmpty());
        verify(actividadRepository, never()).save(any());
    }

    @Test
    void eliminar_deberiaLlamarDeleteById() {
        actividadService.eliminar(1L);

        verify(actividadRepository, times(1)).deleteById(1L);
    }
}