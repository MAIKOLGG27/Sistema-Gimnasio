package com.gym.membrecias.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.membrecias.model.membreciaModel;
import com.gym.membrecias.repository.membreciaRepository;

@Service
public class membreciaService {
   
    @Autowired
    private membreciaRepository repo;

    public List<membreciaModel> obtenerTodas(){
        return repo.findAll();
    }

    public membreciaModel obtenerPorId(Long id){
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Error la membresia con ese id no existe"));
    }

    public membreciaModel guardarMembresia(membreciaModel mem){
        return repo.save(mem);
    }

    public void borrarMembresia(Long id){
        if (!repo.existsById(id)){
            throw new RuntimeException("Error no se puede borrar la membresia con ese id");

        }
        repo.deleteById(id);
    }

    public membreciaModel actualizarMembrecia(Long id, membreciaModel membresiaDetalles){
        return repo.findById(id).map(membresia -> {
            membresia.setNombre(membresiaDetalles.getNombre());
            membresia.setPrecio(membresiaDetalles.getPrecio());
            membresia.setDuracionDias(membresiaDetalles.getDuracionDias());
            return repo.save(membresia);
        }).orElseThrow(()-> new RuntimeException("no se puede actualizar la membresia no existe"));
    }






}
