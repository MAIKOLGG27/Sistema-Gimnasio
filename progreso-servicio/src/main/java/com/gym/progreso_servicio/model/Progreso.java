package com.gym.progreso_servicio.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "progreso")

public class Progreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del cliente es OBLIGATORIO")
    @Column(nullable = false)
    private Long clienteId; // Enlace lógico al microservicio de usuarios

    @NotNull(message = "El peso es OBLIGATORIO")
    @Column(nullable = false)
    private Double peso;

    @NotNull(message = "El porcentaje de grasa es OBLIGATORIO")
    @Column(nullable = false)
    private Double porcentajeGrasa;

    @Column(nullable = true)
    private String observaciones;

    @Column(nullable = false)
    private LocalDate fecha;
    
}
