package cl.gym.reservas.service;

import cl.gym.reservas.model.Reserva;
import cl.gym.reservas.repository.ReservaRepository;
import cl.gym.reservas.webClient.ClienteClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ClienteClient clienteClient;

    @InjectMocks
    private ReservaService reservaService;

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reserva = new Reserva();
        reserva.setCodigoReserva(1L);
        reserva.setClienteId(1L);
        reserva.setNombreCliente("Juan Perez");
        reserva.setNombreClase("Yoga");
        reserva.setInstructor("Maria Lopez");
        reserva.setFechaHoraClase(LocalDateTime.now().plusDays(1));
        reserva.setDuracionMinutos(60);
        reserva.setCapacidadMaxima(20);
        reserva.setEstado("CONFIRMADA");
        reserva.setFechaReserva(LocalDateTime.now());
    }

    @Test
    void obtenerTodos_deberiaRetornarListaDeReservas() {
        when(reservaRepository.findAll()).thenReturn(List.of(reserva));

        List<Reserva> resultado = reservaService.obtenerTodos();

        assertEquals(1, resultado.size());
        verify(reservaRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_deberiaRetornarReserva_cuandoExiste() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        Optional<Reserva> resultado = reservaService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Yoga", resultado.get().getNombreClase());
    }

    @Test
    void obtenerPorId_deberiaRetornarVacio_cuandoNoExiste() {
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Reserva> resultado = reservaService.obtenerPorId(99L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void obtenerPorClienteId_deberiaRetornarReservasDelCliente() {
        when(reservaRepository.findByClienteId(1L)).thenReturn(List.of(reserva));

        List<Reserva> resultado = reservaService.obtenerPorClienteId(1L);

        assertEquals(1, resultado.size());
        verify(reservaRepository, times(1)).findByClienteId(1L);
    }

    @Test
    void obtenerPorEstado_deberiaRetornarReservasConEseEstado() {
        when(reservaRepository.findByEstado("CONFIRMADA")).thenReturn(List.of(reserva));

        List<Reserva> resultado = reservaService.obtenerPorEstado("CONFIRMADA");

        assertEquals(1, resultado.size());
        verify(reservaRepository, times(1)).findByEstado("CONFIRMADA");
    }

    @Test
    void guardar_deberiaAsignarNombreClienteEstadoYFecha() {
        Reserva nueva = new Reserva();
        nueva.setClienteId(1L);
        nueva.setNombreClase("Pilates");
        nueva.setInstructor("Carlos Ruiz");
        nueva.setFechaHoraClase(LocalDateTime.now().plusDays(2));
        nueva.setDuracionMinutos(45);
        nueva.setCapacidadMaxima(15);

        when(clienteClient.obtenerNombreCliente(1L)).thenReturn("Juan Perez");
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Reserva resultado = reservaService.guardar(nueva);

        assertEquals("Juan Perez", resultado.getNombreCliente());
        assertEquals("CONFIRMADA", resultado.getEstado());
        assertNotNull(resultado.getFechaReserva());
        verify(clienteClient, times(1)).obtenerNombreCliente(1L);
        verify(reservaRepository, times(1)).save(nueva);
    }

    @Test
    void guardar_deberiaAsignarSinCliente_cuandoClienteClientFalla() {
        Reserva nueva = new Reserva();
        nueva.setClienteId(99L);
        nueva.setNombreClase("Spinning");
        nueva.setInstructor("Ana Torres");
        nueva.setFechaHoraClase(LocalDateTime.now().plusDays(1));
        nueva.setDuracionMinutos(30);
        nueva.setCapacidadMaxima(10);

        when(clienteClient.obtenerNombreCliente(99L)).thenReturn("Sin cliente");
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Reserva resultado = reservaService.guardar(nueva);

        assertEquals("Sin cliente", resultado.getNombreCliente());
    }

    @Test
    void actualizar_deberiaActualizarYRetornarReserva_cuandoExiste() {
        Reserva detalles = new Reserva();
        detalles.setNombreClase("Crossfit");
        detalles.setInstructor("Pedro Gomez");
        detalles.setFechaHoraClase(LocalDateTime.now().plusDays(3));
        detalles.setDuracionMinutos(50);
        detalles.setCapacidadMaxima(12);
        detalles.setEstado("PENDIENTE");

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        Optional<Reserva> resultado = reservaService.actualizar(1L, detalles);

        assertTrue(resultado.isPresent());
        assertEquals("Crossfit", resultado.get().getNombreClase());
        assertEquals("Pedro Gomez", resultado.get().getInstructor());
        assertEquals("PENDIENTE", resultado.get().getEstado());
        verify(reservaRepository, times(1)).save(reserva);
    }

    @Test
    void actualizar_deberiaRetornarVacio_cuandoNoExiste() {
        Reserva detalles = new Reserva();
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Reserva> resultado = reservaService.actualizar(99L, detalles);

        assertTrue(resultado.isEmpty());
        verify(reservaRepository, never()).save(any());
    }

    @Test
    void eliminar_deberiaLlamarDeleteById() {
        reservaService.eliminar(1L);

        verify(reservaRepository, times(1)).deleteById(1L);
    }
}