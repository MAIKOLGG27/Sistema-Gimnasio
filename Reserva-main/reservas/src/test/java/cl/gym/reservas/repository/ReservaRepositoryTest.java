package cl.gym.reservas.repository;

import cl.gym.reservas.model.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReservaRepositoryTest {

    @Autowired
    private ReservaRepository reservaRepository;

    @BeforeEach
    void setUp() {
        reservaRepository.deleteAll();
    }

    private Reserva buildReserva(Long clienteId, String nombreClase, String estado) {
        Reserva reserva = new Reserva();
        reserva.setClienteId(clienteId);
        reserva.setNombreCliente("Cliente Test");
        reserva.setNombreClase(nombreClase);
        reserva.setInstructor("Instructor Test");
        reserva.setFechaHoraClase(LocalDateTime.now().plusDays(1));
        reserva.setDuracionMinutos(60);
        reserva.setCapacidadMaxima(20);
        reserva.setEstado(estado);
        reserva.setFechaReserva(LocalDateTime.now());
        return reserva;
    }

    @Test
    void deberiaGuardarYRecuperarReserva() {
        Reserva reserva = buildReserva(1L, "Yoga", "CONFIRMADA");
        Reserva guardada = reservaRepository.save(reserva);

        assertNotNull(guardada.getCodigoReserva());
        assertEquals("Yoga", guardada.getNombreClase());
    }

    @Test
    void deberiaEncontrarPorClienteId() {
        reservaRepository.save(buildReserva(1L, "Yoga", "CONFIRMADA"));
        reservaRepository.save(buildReserva(1L, "Pilates", "CONFIRMADA"));
        reservaRepository.save(buildReserva(2L, "Spinning", "CONFIRMADA"));

        List<Reserva> resultado = reservaRepository.findByClienteId(1L);

        assertEquals(2, resultado.size());
    }

    @Test
    void deberiaRetornarListaVacia_cuandoClienteIdNoExiste() {
        List<Reserva> resultado = reservaRepository.findByClienteId(99L);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deberiaEncontrarPorEstado() {
        reservaRepository.save(buildReserva(1L, "Yoga", "CONFIRMADA"));
        reservaRepository.save(buildReserva(2L, "Pilates", "PENDIENTE"));
        reservaRepository.save(buildReserva(3L, "Spinning", "CONFIRMADA"));

        List<Reserva> resultado = reservaRepository.findByEstado("CONFIRMADA");

        assertEquals(2, resultado.size());
    }

    @Test
    void deberiaEncontrarPorNombreClase() {
        reservaRepository.save(buildReserva(1L, "Yoga", "CONFIRMADA"));
        reservaRepository.save(buildReserva(2L, "Yoga", "PENDIENTE"));
        reservaRepository.save(buildReserva(3L, "Spinning", "CONFIRMADA"));

        List<Reserva> resultado = reservaRepository.findByNombreClase("Yoga");

        assertEquals(2, resultado.size());
    }

    @Test
    void deberiaEliminarReserva() {
        Reserva guardada = reservaRepository.save(buildReserva(1L, "Yoga", "CONFIRMADA"));

        reservaRepository.deleteById(guardada.getCodigoReserva());

        assertTrue(reservaRepository.findById(guardada.getCodigoReserva()).isEmpty());
    }
}