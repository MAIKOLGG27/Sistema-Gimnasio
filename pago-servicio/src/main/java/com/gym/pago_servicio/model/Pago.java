package com.gym.pago_servicio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "pagos")
@Schema(description = "Modelo del pago de la membrecia")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del pago",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long id;

    @NotNull(message = "El ID del socio es obligatorio")
    @Schema(description = "Identificador del socio al que pertenece el pago",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED
    )           
    private Long socioId; 

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0") 
    @Schema(description = "Costo de la membresia",
            example = "15000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )      
    private Double monto;

    @Schema(description = "Metodo de pago",
            example = "Visa",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String metodoPago;


    @Schema(description = "Estado en que se encuentran los pagos de la membresia",
            example = "Pagado",
            requiredMode = Schema.RequiredMode.REQUIRED
    )    
    private String estado;

    @Schema(description = "Fecha del pago",
            example = "2026-06-16T15:30:00.000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )  
    private LocalDateTime fechaPago;

    @PrePersist
    public void prePersist() {
        if (fechaPago == null) fechaPago = LocalDateTime.now();
        if (estado == null) estado = "PENDIENTE";
    }
}
