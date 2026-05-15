package cl.gym.ActividadesDeportivas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.gym.ActividadesDeportivas.model.Actividad;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {

}
