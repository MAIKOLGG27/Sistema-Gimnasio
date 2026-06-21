package cl.gym.ActividadesDeportivas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Actividad_Deportiva")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idActividad;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Column(nullable = false, unique = true)
    private String nombre;

    @NotBlank(message = "La descripcion no puede estar vacia")
    @Column(nullable = false)
    private String descripcion;

}
