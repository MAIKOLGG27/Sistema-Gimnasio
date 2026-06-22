package com.gym.progreso_servicio.repository;

import com.gym.progreso_servicio.model.Progreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProgresoRepository extends JpaRepository<Progreso, Long> {
    
   
    List<Progreso> findByClientId(Long clientId);
}