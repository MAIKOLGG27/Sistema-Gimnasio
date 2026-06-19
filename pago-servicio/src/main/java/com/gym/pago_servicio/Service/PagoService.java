package com.gym.pago_servicio.Service;

import com.gym.pago_servicio.model.Pago;

import jakarta.persistence.EntityNotFoundException;

import com.gym.pago_servicio.Repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository repo;

    public List<Pago> obtenerTodas(){
        return repo.findAll();
    }

    public Pago registrarPago(Pago pago){
        pago.setFechaPago(LocalDateTime.now());
        if (pago.getEstado() == null) {
            pago.setEstado("PENDIENTE");
        }
        return repo.save(pago);
    }

    public Optional<Pago> buscarPorId(Long id){
        return repo.findById(id);
    }

    public Pago actualizarPago(Long id, Pago pagoActualizado){
        return repo.findById(id).map(pago -> {
            pago.setMonto(pagoActualizado.getMonto());
            pago.setMetodoPago(pagoActualizado.getMetodoPago());
            pago.setEstado(pagoActualizado.getEstado());
            pago.setFechaPago(pagoActualizado.getFechaPago() != null ? pagoActualizado.getFechaPago() : LocalDateTime.now());
            return repo.save(pago);
        }).orElseThrow(() -> new EntityNotFoundException("Pago no encontrado con ID: " + id));
    }

    public void borrarPago(Long id){
        repo.deleteById(id);
    }
    
    public List<Pago> buscarPorSocioId(Long socioId) {
        return repo.findBySocioId(socioId);
    }
}
