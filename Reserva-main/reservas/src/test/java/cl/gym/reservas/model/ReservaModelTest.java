package cl.gym.reservas.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ReservaModelTest {

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

    private Reserva buildReservaValida() {
        Reserva reserva = new Reserva();
        reserva.setClienteId(1L);
        reserva.setNombreCliente("Juan Perez");
        reserva.setNombreClase("Yoga");
        reserva.setInstructor("Maria Lopez");
        reserva.setFechaHoraClase(LocalDateTime.now().plusDays(1));
        reserva.setDuracionMinutos(60);
        reserva.setCapacidadMaxima(20);
        reserva.setEstado("CONFIRMADA");
        reserva.setFechaReserva(LocalDateTime.now());
        return reserva;
    }

    @Test
    void deberiaSerValida_cuandoTodosLosCamposEstanCorrectos() {
        Reserva reserva = buildReservaValida();
        Set<ConstraintViolation<Reserva>> violaciones = validator.validate(reserva);
        assertTrue(violaciones.isEmpty());
    }

    @Test
    void deberiaFallar_cuandoClienteIdEsNull() {
        Reserva reserva = buildReservaValida();
        reserva.setClienteId(null);
        Set<ConstraintViolation<Reserva>> violaciones = validator.validate(reserva);
        assertFalse(violaciones.isEmpty());
        assertEquals("El ID del cliente es obligatorio",
                violaciones.iterator().next().getMessage());
    }

    @Test
    void deberiaFallar_cuandoNombreClaseEstaVacio() {
        Reserva reserva = buildReservaValida();
        reserva.setNombreClase("");
        Set<ConstraintViolation<Reserva>> violaciones = validator.validate(reserva);
        assertFalse(violaciones.isEmpty());
        assertEquals("El nombre de la clase es obligatorio",
                violaciones.iterator().next().getMessage());
    }

    @Test
    void deberiaFallar_cuandoNombreClaseEsNull() {
        Reserva reserva = buildReservaValida();
        reserva.setNombreClase(null);
        Set<ConstraintViolation<Reserva>> violaciones = validator.validate(reserva);
        assertFalse(violaciones.isEmpty());
    }

    @Test
    void deberiaFallar_cuandoInstructorEstaVacio() {
        Reserva reserva = buildReservaValida();
        reserva.setInstructor("");
        Set<ConstraintViolation<Reserva>> violaciones = validator.validate(reserva);
        assertFalse(violaciones.isEmpty());
        assertEquals("El instructor es obligatorio",
                violaciones.iterator().next().getMessage());
    }

    @Test
    void deberiaFallar_cuandoFechaHoraClaseEsNull() {
        Reserva reserva = buildReservaValida();
        reserva.setFechaHoraClase(null);
        Set<ConstraintViolation<Reserva>> violaciones = validator.validate(reserva);
        assertFalse(violaciones.isEmpty());
        assertEquals("La fecha y hora es obligatoria",
                violaciones.iterator().next().getMessage());
    }

    @Test
    void deberiaFallar_cuandoDuracionMinutosEsNull() {
        Reserva reserva = buildReservaValida();
        reserva.setDuracionMinutos(null);
        Set<ConstraintViolation<Reserva>> violaciones = validator.validate(reserva);
        assertFalse(violaciones.isEmpty());
        assertEquals("La duracion es obligatoria",
                violaciones.iterator().next().getMessage());
    }

    @Test
    void deberiaFallar_cuandoCapacidadMaximaEsNull() {
        Reserva reserva = buildReservaValida();
        reserva.setCapacidadMaxima(null);
        Set<ConstraintViolation<Reserva>> violaciones = validator.validate(reserva);
        assertFalse(violaciones.isEmpty());
        assertEquals("La capacidad maxima es obligatoria",
                violaciones.iterator().next().getMessage());
    }

    @Test
    void getterYSetter_funcionanCorrectamente() {
        LocalDateTime fechaClase = LocalDateTime.now().plusDays(2);
        Reserva reserva = new Reserva();
        reserva.setCodigoReserva(1L);
        reserva.setClienteId(5L);
        reserva.setNombreCliente("Ana Torres");
        reserva.setNombreClase("Pilates");
        reserva.setInstructor("Carlos Ruiz");
        reserva.setFechaHoraClase(fechaClase);
        reserva.setDuracionMinutos(45);
        reserva.setCapacidadMaxima(15);
        reserva.setEstado("PENDIENTE");

        assertEquals(1L, reserva.getCodigoReserva());
        assertEquals(5L, reserva.getClienteId());
        assertEquals("Ana Torres", reserva.getNombreCliente());
        assertEquals("Pilates", reserva.getNombreClase());
        assertEquals("Carlos Ruiz", reserva.getInstructor());
        assertEquals(fechaClase, reserva.getFechaHoraClase());
        assertEquals(45, reserva.getDuracionMinutos());
        assertEquals(15, reserva.getCapacidadMaxima());
        assertEquals("PENDIENTE", reserva.getEstado());
    }
}