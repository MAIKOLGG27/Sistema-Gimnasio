package com.gym.entrenador_servicio.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "entrenadores")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo del entrenador del gimnasio")
public class Entrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del entrenador",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long id;

    @Schema(description = "Nombre del entrenador",
            example = "Carlos",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String nombre;

    @Schema(description = "Apellido del entrenador",
            example = "González",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String apellido;

    @Schema(description = "Especialidad del entrenador",
            example = "CrossFit",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String especialidad;

    @Schema(description = "Teléfono de contacto del entrenador",
            example = "+56987654321",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String telefono;

    @Schema(description = "Correo electrónico del entrenador",
            example = "carlos.gonzalez@gym.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @Schema(description = "Indica si el entrenador está activo",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Boolean activo;

    @PrePersist
    public void prePersist() {
        if (activo == null) {
            activo = true;
        }
    }
}
