package com.gym.entrenador_servicio.Service;

import com.gym.entrenador_servicio.Model.Entrenador;
import com.gym.entrenador_servicio.Repository.EntrenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class EntrenadorService {

    @Autowired
    private EntrenadorRepository repo;

    public List<Entrenador> obtenerTodas(){
        return repo.findAll();
    }

    public Entrenador registrarEntrenador(Entrenador entrenador){
        return repo.save(entrenador);
    }

    public Optional<Entrenador> buscarPorId(Long id){
        return repo.findById(id);
    }

    public Entrenador actualizarEntrenador(Long id, Entrenador entrenadorActualizado){
        return repo.findById(id).map(entrenador -> {
            entrenador.setNombre(entrenadorActualizado.getNombre());
            entrenador.setApellido(entrenadorActualizado.getApellido());
            entrenador.setEspecialidad(entrenadorActualizado.getEspecialidad());
            entrenador.setTelefono(entrenadorActualizado.getTelefono());
            entrenador.setEmail(entrenadorActualizado.getEmail());
            entrenador.setActivo(entrenadorActualizado.getActivo());
            return repo.save(entrenador);
        }).orElseThrow(() -> new RuntimeException("Entrenador no encontrado con ID: " + id));
    }
    
    public void borrarEntrenador(Long id){
        repo.deleteById(id);
    }
}
