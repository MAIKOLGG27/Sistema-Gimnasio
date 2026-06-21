package com.gym.asistencia_servicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.asistencia_servicio.model.AsistenciaModel;

public interface AsistenciaRepository extends JpaRepository<AsistenciaModel, Long>{

}
