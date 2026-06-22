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
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "progreso")
@Schema(description = "Modelo que representa el registro de progreso físico de un cliente")
public class Progreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @NotNull(message = "El ID del cliente es OBLIGATORIO")
    @Column(nullable = false)
    @Schema(description = "ID del cliente", example = "105", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long clienteId; 

    @NotNull(message = "El peso es OBLIGATORIO")
    @Column(nullable = false)
    @Schema(description = "Peso del cliente", example = "75.5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double peso;

    @NotNull(message = "El porcentaje de grasa es OBLIGATORIO")
    @Column(nullable = false)
    @Schema(description = "Porcentaje de grasa corporal", example = "18.5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double porcentajeGrasa;

    @Column(nullable = true)
    @Schema(description = "Observaciones adicionales", example = "El cliente mejoró su resistencia", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String observaciones;

    @Column(nullable = false)
    @Schema(description = "Fecha de la medición", example = "2026-06-21", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fecha;
}
