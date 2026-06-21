package com.gym.asistencia_servicio.model;

import java.time.LocalDateTime;

import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "Asistencias")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo de la asistencia de un usuario al gimnasio")
public class AsistenciaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador de la asistencia",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long id;

    @Schema(description = "Identificador del usuario que registra la asistencia",
            example = "10",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long usuarioId;

    @Schema(description = "Fecha y hora en la que se registró el ingreso",
            example = "2026-06-21T09:30:00"
    )
    private LocalDateTime fechaHoraEntrada;

}
