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



    public List<ContratoModel> obtenerTodas() {
        return repo.findAll();
    }

    public ContratoModel registrarContrato(ContratoModel contrato){
        return repo.save(contrato);
    }

    public void borrarContrato(Long id) {
        repo.deleteById(id);
    }

    public ContratoModel actualizarContrato(Long id, ContratoModel contratoDetalles) {
        return repo.findById(id).map(contrato -> {
            contrato.setUsuarioId(contratoDetalles.getUsuarioId());
            contrato.setMebresiaId(contratoDetalles.getMebresiaId()); // Ojo: Verifica si es 'membresiaId'
            contrato.setFechaInicio(contratoDetalles.getFechaInicio());
            contrato.setFechafin(contratoDetalles.getFechafin());
            contrato.setEstado(contratoDetalles.getEstado());
            return repo.save(contrato);
        }).orElse(null);
    }
}