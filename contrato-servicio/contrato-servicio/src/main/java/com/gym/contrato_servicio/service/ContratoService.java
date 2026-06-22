package com.gym.contrato_servicio.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gym.contrato_servicio.model.ContratoModel;
import com.gym.contrato_servicio.repository.ContratoRepository;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository repo;

    
    public ContratoModel obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: El contrato con id " + id + " no existe"));
    }

    public List<ContratoModel> obtenerTodas() {
        return repo.findAll();
    }

    public ContratoModel registrarContrato(ContratoModel contrato){
        return repo.save(contrato);
    }

    public void borrarContrato(Long id) {
        if (!repo.existsById(id)){
            throw new RuntimeException("Error: no se puede borrar el contrato, id no existe");
        }
        repo.deleteById(id);
    }

    public ContratoModel actualizarContrato(Long id, ContratoModel contratoDetalles) {
        return repo.findById(id).map(contrato -> {
            contrato.setUsuarioId(contratoDetalles.getUsuarioId());
            contrato.setMebresiaId(contratoDetalles.getMebresiaId()); 
            contrato.setFechaInicio(contratoDetalles.getFechaInicio());
            contrato.setFechafin(contratoDetalles.getFechafin());
            contrato.setEstado(contratoDetalles.getEstado());
            return repo.save(contrato);
        }).orElseThrow(() -> new RuntimeException("Error: no se puede actualizar, el contrato no existe"));
    }
}