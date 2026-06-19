package com.gym.registro_servicio.Service;

import com.gym.registro_servicio.Model.Registro;
import com.gym.registro_servicio.Repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gym.registro_servicio.webPago.PagoPag;
import java.util.Map;

import java.util.List;
import java.util.Optional;

@Service
public class RegistroService {

    @Autowired
    private RegistroRepository repo;

    @Autowired
    private PagoPag pagoPag;

    public List<Registro> obtenerTodas(){
        return repo.findAll();
    }

    public Registro registrarRegistro(Registro nuevoRegistro){
        return repo.save(nuevoRegistro);
    }
    public Optional<Registro> buscarPorId(Long id){
        return repo.findById(id);
    }

    public Registro actualizarRegistro(Long id, Registro registroActualizado){
        return repo.findById(id).map(registro -> {
            registro.setNombre(registroActualizado.getNombre());
            registro.setApellido(registroActualizado.getApellido());
            registro.setEmail(registroActualizado.getEmail());
            registro.setTelefono(registroActualizado.getTelefono());
            registro.setActivo(registroActualizado.getActivo());
            return repo.save(registro);
        }).orElseThrow(() -> new RuntimeException("registro no encontrado con ID: " + id));
    }
    
    public void borrarRegistro(Long id){
        repo.deleteById(id);
    }

    public Map obtenerRegistroConPago(Long id) {
        Registro registro = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("No encontrado"));

        Map pago = pagoPag.obtenerPago(registro.getPagoId());

        return Map.of(
            "registro", registro,
            "pago", pago != null ? pago : "Sin pago"
        );
    }
}