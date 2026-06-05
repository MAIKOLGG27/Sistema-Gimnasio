package com.gym.pago_servicio.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "pagos")

public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;                
    private Long socioId;           
    private Double monto;
    private String metodoPago;      
    private String estado;     
    private LocalDateTime fechaPago;

    @PrePersist
    public void prePersist() {
        if (fechaPago == null) fechaPago = LocalDateTime.now();
        if (estado == null) estado = "PENDIENTE";
    }
}
