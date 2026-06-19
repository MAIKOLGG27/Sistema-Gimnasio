package com.gym.entrenador_servicio.Service;

import com.gym.entrenador_servicio.Model.Entrenador;
import com.gym.entrenador_servicio.Repository.EntrenadorRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class EntrenadorServiceTest {

    @Autowired
    private EntrenadorService entrenadorService;

    @MockitoBean
    private EntrenadorRepository entrenadorRepository;

    @Test
    public void testObtenerTodas() {
        Entrenador entrenador = crearEntrenador();
        when(entrenadorRepository.findAll()).thenReturn(List.of(entrenador));

        List<Entrenador> entrenadores = entrenadorService.obtenerTodas();
        assertNotNull(entrenadores);
        assertEquals(1, entrenadores.size());
        assertEquals(entrenador.getId(), entrenadores.get(0).getId());
    }

    @Test
    public void testBuscarPorId() {
        Long id = 1L;
        Entrenador entrenador = crearEntrenador();
        when(entrenadorRepository.findById(id)).thenReturn(Optional.of(entrenador));

        Optional<Entrenador> encontrado = entrenadorService.buscarPorId(id);
        assertTrue(encontrado.isPresent());
        assertEquals(id, encontrado.get().getId());
    }
    
    @Test
    public void testBuscarPorId_NoExiste() {
        Long id = 99L;
        when(entrenadorRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Entrenador> encontrado = entrenadorService.buscarPorId(id);
        assertFalse(encontrado.isPresent());
    }

    @Test
    public void testRegistrarEntrenador() {
        Entrenador entrenador = crearEntrenador();
        when(entrenadorRepository.save(any(Entrenador.class))).thenReturn(entrenador);

        Entrenador guardado = entrenadorService.registrarEntrenador(entrenador);
        assertNotNull(guardado);
        assertEquals(entrenador.getId(), guardado.getId());
        verify(entrenadorRepository, times(1)).save(entrenador);
    }

    @Test
    public void testActualizarEntrenador() {
        Long id = 1L;
        Entrenador existente = crearEntrenador();
        Entrenador actualizado = crearEntrenador();
        actualizado.setNombre("Marcos");
        actualizado.setApellido("Reyes");
        actualizado.setEspecialidad("Yoga");
        actualizado.setTelefono("+56911112222");
        actualizado.setEmail("marcos.reyes@gym.com");
        actualizado.setActivo(false);

        when(entrenadorRepository.findById(id)).thenReturn(Optional.of(existente));
        when(entrenadorRepository.save(any(Entrenador.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Entrenador resultado = entrenadorService.actualizarEntrenador(id, actualizado);
        assertNotNull(resultado);
        assertEquals("Marcos", resultado.getNombre());
        assertEquals("Reyes", resultado.getApellido());
        assertEquals("Yoga", resultado.getEspecialidad());
        assertEquals("marcos.reyes@gym.com", resultado.getEmail());
        assertFalse(resultado.getActivo());
    }

    @Test
    public void testActualizarEntrenador_NoExiste() {
        Long id = 99L;
        Entrenador actualizado = crearEntrenador();
        when(entrenadorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            entrenadorService.actualizarEntrenador(id, actualizado);
        });
    }

    @Test
    public void testBorrarEntrenador() {
        Long id = 1L;
        doNothing().when(entrenadorRepository).deleteById(id);

        entrenadorService.borrarEntrenador(id);
        verify(entrenadorRepository, times(1)).deleteById(id);
    }

    private Entrenador crearEntrenador() {
        Entrenador entrenador = new Entrenador();
        entrenador.setId(1L);
        entrenador.setNombre("Carlos");
        entrenador.setApellido("González");
        entrenador.setEspecialidad("CrossFit");
        entrenador.setTelefono("+56987654321");
        entrenador.setEmail("carlos.gonzalez@gym.com");
        entrenador.setActivo(true);
        return entrenador;
    }
}
