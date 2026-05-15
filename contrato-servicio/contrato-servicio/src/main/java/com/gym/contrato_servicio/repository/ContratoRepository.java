package com.gym.contrato_servicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.contrato_servicio.model.ContratoModel;

public interface ContratoRepository extends JpaRepository<ContratoModel, Long> {

}
