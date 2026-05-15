package com.gym.Clientes.model;

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
public class cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El RUT es OBLIGATORIO")
    @Column(unique = true, nullable = false)
    private String rut;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Column(unique = true, nullable = false)
    private String nombre;

    @NotBlank(message = "El password no puede estar vacio")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "El correo no puede estar vacio")
    @Column(unique = true, nullable = false)
    private String correo;

    @NotNull(message = "El telefono es OBLIGATORIO")
    @Column(nullable = false)
    private Integer telefono;


}
