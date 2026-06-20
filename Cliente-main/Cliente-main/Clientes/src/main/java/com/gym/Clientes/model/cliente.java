package com.gym.Clientes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
@Schema(description = "Entidad que representa a un cliente del gimnasio")
public class cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del cliente", example = "1")
    private Long id;

    @NotBlank(message = "El RUT es OBLIGATORIO")
    @Column(unique = true, nullable = false)
    @Schema(description = "RUT del cliente, debe ser único", example = "12345678-9")
    private String rut;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Column(unique = true, nullable = false)
    @Schema(description = "Nombre completo del cliente", example = "Juan Pérez")
    private String nombre;

    @NotBlank(message = "El password no puede estar vacio")
    @Column(nullable = false)
    @Schema(description = "Contraseña del cliente, debe ser segura", example = "P@ssw0rd")
    private String password;

    @NotBlank(message = "El correo no puede estar vacio")
    @Column(unique = true, nullable = false)
    @Schema(description = "Correo electrónico del cliente, debe ser único", example = "juan.perez@example.com")
    private String correo;

    @NotNull(message = "El telefono es OBLIGATORIO")
    @Column(nullable = false)
    @Schema(description = "Número de teléfono del cliente", example = "987654321")
    private Integer telefono;


}
