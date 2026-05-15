package com.gym.entrenador_servicio.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "entrenadores")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String especialidad;   // Ej: "CrossFit", "Yoga", "Musculación", etc.
    private String telefono;
    private String email;
    private Boolean activo;

    @PrePersist
    public void prePersist() {
        if (activo == null) {
            activo = true;
        }
    }
}