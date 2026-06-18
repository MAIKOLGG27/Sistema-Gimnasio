package com.gym.membrecias.model;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "membresias")
@Schema(description = "El modelo de la membresia")
public class membreciaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
        description = "Identificador de membresia",
        example = "2",
        requiredMode = Schema.RequiredMode.REQUIRED
        
    )
    private Long id;

     @Schema(
        description = "Nombre de la membresia",
        example = "Superpromo",
        requiredMode = Schema.RequiredMode.REQUIRED
    )

    private String nombre;

     @Schema(
        description =  "Precio de la membresia",
        example = "30.000",
        requiredMode = Schema.RequiredMode.REQUIRED
     )
   
    private Double precio;
     
     @Schema(
        description = "Duracion de los dias",
        example = "30",
        requiredMode = Schema.RequiredMode.REQUIRED
     )

    private Integer duracionDias;

}
