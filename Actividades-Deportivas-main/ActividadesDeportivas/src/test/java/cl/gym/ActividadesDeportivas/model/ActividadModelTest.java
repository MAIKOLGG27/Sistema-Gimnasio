package cl.gym.ActividadesDeportivas.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ActividadModelTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }

    @Test
    void deberiaSerValida_cuandoTodosLosCamposEstanCorrectos() {
        Actividad actividad = new Actividad(null, "Yoga", "Clase de yoga para todo nivel");
        Set<ConstraintViolation<Actividad>> violaciones = validator.validate(actividad);
        assertTrue(violaciones.isEmpty());
    }

    @Test
    void deberiaFallar_cuandoNombreEstaVacio() {
        Actividad actividad = new Actividad(null, "", "Descripcion valida");
        Set<ConstraintViolation<Actividad>> violaciones = validator.validate(actividad);
        assertFalse(violaciones.isEmpty());
        assertEquals("El nombre no puede estar vacio",
                violaciones.iterator().next().getMessage());
    }

    @Test
    void deberiaFallar_cuandoNombreEsNull() {
        Actividad actividad = new Actividad(null, null, "Descripcion valida");
        Set<ConstraintViolation<Actividad>> violaciones = validator.validate(actividad);
        assertFalse(violaciones.isEmpty());
    }

    @Test
    void deberiaFallar_cuandoDescripcionEstaVacia() {
        Actividad actividad = new Actividad(null, "Pilates", "");
        Set<ConstraintViolation<Actividad>> violaciones = validator.validate(actividad);
        assertFalse(violaciones.isEmpty());
        assertEquals("La descripcion no puede estar vacia",
                violaciones.iterator().next().getMessage());
    }

    @Test
    void deberiaFallar_cuandoDescripcionEsNull() {
        Actividad actividad = new Actividad(null, "Pilates", null);
        Set<ConstraintViolation<Actividad>> violaciones = validator.validate(actividad);
        assertFalse(violaciones.isEmpty());
    }

    @Test
    void deberiaTenerDosViolaciones_cuandoNombreYDescripcionEstanVacios() {
        Actividad actividad = new Actividad(null, "", "");
        Set<ConstraintViolation<Actividad>> violaciones = validator.validate(actividad);
        assertEquals(2, violaciones.size());
    }

    @Test
    void getterYSetter_funcionanCorrectamente() {
        Actividad actividad = new Actividad();
        actividad.setIdActividad(1L);
        actividad.setNombre("Spinning");
        actividad.setDescripcion("Clase de ciclismo indoor");

        assertEquals(1L, actividad.getIdActividad());
        assertEquals("Spinning", actividad.getNombre());
        assertEquals("Clase de ciclismo indoor", actividad.getDescripcion());
    }
}