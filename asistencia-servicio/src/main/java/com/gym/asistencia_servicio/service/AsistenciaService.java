package com.gym.asistencia_servicio.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.asistencia_servicio.model.AsistenciaModel;
import com.gym.asistencia_servicio.repository.AsistenciaRepository;



@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository repo;

    public List<AsistenciaModel> obtenerTodas(){
        return repo.findAll();

    }

    public AsistenciaModel registrarAsistencia(AsistenciaModel asistencia){
        asistencia.setFechaHoraEntrada(LocalDateTime.now());
        return repo.save(asistencia);
    }

    public AsistenciaModel actualizarAsistencia(Long id, AsistenciaModel asistencia){
        if(repo.existsById(id)){
            asistencia.setId(id);
            return repo.save(asistencia);
        }
        return null;
    }

    public void borrarAsistencia(Long Id){
        repo.deleteById(Id);
    }

    public AsistenciaModel buscarPorId(Long Id){
        return repo.findById(Id).orElse(null);
    }




}
