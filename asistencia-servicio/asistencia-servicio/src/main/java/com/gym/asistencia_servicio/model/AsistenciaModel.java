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

@Entity
@Table(name = "Asistencias")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsistenciaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long usuarioId;
    private LocalDateTime fechaHoraEntrada;


}
