package cl.gym.reservas.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.gym.reservas.model.Reserva;
import cl.gym.reservas.repository.ReservaRepository;
import cl.gym.reservas.webClient.ClienteClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteClient clienteClient;

    // ── OBTENER TODOS ────────────────────────────────
    public List<Reserva> obtenerTodos() {
        return reservaRepository.findAll();
    }

    // ── OBTENER POR ID ───────────────────────────────
    public Optional<Reserva> obtenerPorId(Long id) {
        return reservaRepository.findById(id);
    }

    // ── OBTENER POR CLIENTE ──────────────────────────
    public List<Reserva> obtenerPorClienteId(Long clienteId) {
        return reservaRepository.findByClienteId(clienteId);
    }

    // ── OBTENER POR ESTADO ───────────────────────────
    public List<Reserva> obtenerPorEstado(String estado) {
        return reservaRepository.findByEstado(estado);
    }

    // ── GUARDAR ──────────────────────────────────────
    public Reserva guardar(Reserva reserva) {
        String nombreCliente = clienteClient.obtenerNombreCliente(reserva.getClienteId());
        reserva.setNombreCliente(nombreCliente);
        reserva.setEstado("CONFIRMADA");
        reserva.setFechaReserva(LocalDateTime.now());
        return reservaRepository.save(reserva);
    }

    // ── ACTUALIZAR ───────────────────────────────────
    public Optional<Reserva> actualizar(Long id, Reserva reservaDetails) {
        return reservaRepository.findById(id).map(existente -> {
            existente.setNombreClase(reservaDetails.getNombreClase());
            existente.setInstructor(reservaDetails.getInstructor());
            existente.setFechaHoraClase(reservaDetails.getFechaHoraClase());
            existente.setDuracionMinutos(reservaDetails.getDuracionMinutos());
            existente.setCapacidadMaxima(reservaDetails.getCapacidadMaxima());
            existente.setEstado(reservaDetails.getEstado());
            return reservaRepository.save(existente);
        });
    }

    // ── ELIMINAR ─────────────────────────────────────
    public void eliminar(Long id) {
        reservaRepository.deleteById(id);
    }

}
