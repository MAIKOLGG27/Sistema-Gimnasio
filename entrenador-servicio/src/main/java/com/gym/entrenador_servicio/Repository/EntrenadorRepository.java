package com.gym.entrenador_servicio.Repository;

import com.gym.entrenador_servicio.Model.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {
}
