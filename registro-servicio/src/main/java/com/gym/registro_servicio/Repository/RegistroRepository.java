package com.gym.registro_servicio.Repository;

import com.gym.registro_servicio.Model.Registro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroRepository extends JpaRepository<Registro, Long> {
}
