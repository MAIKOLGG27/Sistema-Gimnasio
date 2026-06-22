package com.gym.contrato_servicio.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contratos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "El modelo que representa el contrato de un cliente con una membresía")
public class ContratoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
        description = "Identificador único del contrato",
        example = "1",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long id;

    @Schema(
        description = "Identificador del usuario (cliente) que contrata",
        example = "105",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long usuarioId;

    @Schema(
        description = "Identificador de la membresía contratada",
        example = "2",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long mebresiaId;

    @Schema(
        description = "Fecha de inicio del contrato",
        example = "2026-06-21",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDate fechaInicio;

    @Schema(
        description = "Fecha en la que termina el contrato",
        example = "2026-07-21",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDate fechafin;

    @Schema(
        description = "Estado actual del contrato",
        example = "ACTIVO",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String estado;

}