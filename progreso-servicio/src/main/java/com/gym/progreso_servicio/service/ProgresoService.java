package com.gym.progreso_servicio.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.gym.progreso_servicio.model.Progreso;
import com.gym.progreso_servicio.repository.ProgresoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgresoService {

    private final ProgresoRepository progresoRepository;

    public List<Progreso> obtenerTodos() {
        return progresoRepository.findAll();
    }

    public Optional<Progreso> obtenerPorId(Long id) {
        return progresoRepository.findById(id);
    }

    public List<Progreso> obtenerPorClienteId(Long clienteId) {
        return progresoRepository.findByClienteId(clienteId);
    }

    public Progreso crearProgreso(Progreso progreso) {
        if (progreso.getFecha() == null) {
            progreso.setFecha(LocalDate.now()); // Asigna fecha del servidor si no viene definida
        }
        return progresoRepository.save(progreso);
    }

    public Optional<Progreso> actualizarProgreso(Long id, Progreso nuevoProgreso) {
        return progresoRepository.findById(id).map(progresoExistente -> {
            progresoExistente.setPeso(nuevoProgreso.getPeso());
            progresoExistente.setPorcentajeGrasa(nuevoProgreso.getPorcentajeGrasa());
            progresoExistente.setObservaciones(nuevoProgreso.getObservaciones());
            if (nuevoProgreso.getFecha() != null) {
                progresoExistente.setFecha(nuevoProgreso.getFecha());
            }
            return progresoRepository.save(progresoExistente);
        });
    }

    public void eliminarProgreso(Long id) {
        progresoRepository.deleteById(id);
    }
}