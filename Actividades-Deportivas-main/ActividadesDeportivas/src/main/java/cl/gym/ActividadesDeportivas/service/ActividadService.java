package cl.gym.ActividadesDeportivas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.gym.ActividadesDeportivas.model.Actividad;
import cl.gym.ActividadesDeportivas.repository.ActividadRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActividadService {

    private final ActividadRepository actividadRepository;

    // ── OBTENER TODOS ────────────────────────────────
    public List<Actividad> obtenerTodos() {
        return actividadRepository.findAll();
    }

    // ── OBTENER POR ID ───────────────────────────────
    public Optional<Actividad> obtenerPorId(Long id) {
        return actividadRepository.findById(id);
    }

    // ── GUARDAR ──────────────────────────────────────
    public Actividad guardar(Actividad actividad) {
        return actividadRepository.save(actividad);
    }

    // ── ACTUALIZAR ───────────────────────────────────
    public Optional<Actividad> actualizar(Long id, Actividad actividadDetails) {
        return actividadRepository.findById(id).map(existente -> {
            existente.setNombre(actividadDetails.getNombre());
            existente.setDescripcion(actividadDetails.getDescripcion());
            return actividadRepository.save(existente);
        });
    }

    // ── ELIMINAR ─────────────────────────────────────
    public void eliminar(Long id) {
        actividadRepository.deleteById(id);
    }

}
