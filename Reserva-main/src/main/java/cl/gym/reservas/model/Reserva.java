package cl.gym.reservas.model;

import java.time.LocalDateTime;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoReserva;

    @NotNull(message = "El ID del cliente es obligatorio")
    @Column(nullable = false)
    private Long clienteId;

    @Column(nullable = false)
    private String nombreCliente;

    @NotBlank(message = "El nombre de la clase es obligatorio")
    @Column(nullable = false)
    private String nombreClase;

    @NotBlank(message = "El instructor es obligatorio")
    @Column(nullable = false)
    private String instructor;

    @NotNull(message = "La fecha y hora es obligatoria")
    @Column(nullable = false)
    private LocalDateTime fechaHoraClase;

    @NotNull(message = "La duracion es obligatoria")
    @Column(nullable = false)
    private Integer duracionMinutos;

    @NotNull(message = "La capacidad maxima es obligatoria")
    @Column(nullable = false)
    private Integer capacidadMaxima;

    // Asignados automáticamente por el sistema
    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private LocalDateTime fechaReserva;

}
