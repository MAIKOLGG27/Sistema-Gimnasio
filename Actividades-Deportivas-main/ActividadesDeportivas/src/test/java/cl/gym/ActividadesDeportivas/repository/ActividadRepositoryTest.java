package cl.gym.ActividadesDeportivas.repository;

import cl.gym.ActividadesDeportivas.model.Actividad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:cl/gym/resources/application.properties")
class ActividadRepositoryTest {

    @Autowired
    private ActividadRepository actividadRepository;

    private Actividad actividadGuardada;

    @BeforeEach
    void setUp() {
        actividadRepository.deleteAll();
        Actividad act = new Actividad(null, "Yoga", "Clase de yoga para todo nivel");
        actividadGuardada = actividadRepository.save(act);
    }

    @Test
    void testGuardarActividad() {
        assertNotNull(actividadGuardada.getIdActividad());
        assertEquals("Yoga", actividadGuardada.getNombre());
    }

    @Test
    void testBuscarPorId() {
        Optional<Actividad> resultado = actividadRepository.findById(actividadGuardada.getIdActividad());
        assertTrue(resultado.isPresent());
        assertEquals("Clase de yoga para todo nivel", resultado.get().getDescripcion());
    }

    @Test
    void testBuscarTodos() {
        List<Actividad> lista = actividadRepository.findAll();
        assertFalse(lista.isEmpty());
    }

    @Test
    void testEliminarActividad() {
        actividadRepository.deleteById(actividadGuardada.getIdActividad());
        Optional<Actividad> resultado = actividadRepository.findById(actividadGuardada.getIdActividad());
        assertFalse(resultado.isPresent());
    }
}