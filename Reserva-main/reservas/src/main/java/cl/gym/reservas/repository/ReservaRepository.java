package cl.gym.reservas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.gym.reservas.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long>{
    
    List<Reserva> findByClienteId(Long clienteId);
    List<Reserva> findByEstado(String estado);
    List<Reserva> findByNombreClase(String nombreClase);

}
