package com.gym.contrato_servicio.model;

import java.time.LocalDate;


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
public class ContratoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long usuarioId;
    private long mebresiaId;
    private LocalDate fechaInicio;
    private LocalDate fechafin;
    private String estado;

}
