package com.gym.Clientes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.Clientes.model.cliente;

public interface ClienteRepository extends JpaRepository<cliente, Long> {
    Optional<cliente> findByNombre(String nombre);
}
