package com.gym.registro_servicio.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "registros")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo del registro del socio del gimnasio")
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del registro",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long id;

    @Schema(description = "Identificador del pago asociado al registro",
            example = "3",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long pagoId;

    @Schema(description = "Nombre del socio",
            example = "Juan",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String nombre;

    @Schema(description = "Apellido del socio",
            example = "Pérez",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String apellido;

    @Schema(description = "Correo electrónico del socio",
            example = "juan.perez@email.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @Schema(description = "Teléfono de contacto del socio",
            example = "+56912345678",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String telefono;

    @Schema(description = "Fecha en que se realizó el registro",
            example = "2026-06-17",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDate fechaRegistro;

    @Schema(description = "Indica si el socio está activo",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Boolean activo;

    @PrePersist
    public void prePersist() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDate.now();
        }
        if (activo == null) {
            activo = true;
        }
    }
}
