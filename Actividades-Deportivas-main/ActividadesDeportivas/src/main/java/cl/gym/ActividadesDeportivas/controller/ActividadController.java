package cl.gym.ActividadesDeportivas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.gym.ActividadesDeportivas.model.Actividad;
import cl.gym.ActividadesDeportivas.service.ActividadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/actividades")
@RequiredArgsConstructor
public class ActividadController {

    private final ActividadService actividadService;

    // GET /api/actividades → 200 OK con lista
    @GetMapping
    public ResponseEntity<List<Actividad>> obtenerTodos() {
        return ResponseEntity.ok(actividadService.obtenerTodos());
    }

    // GET /api/actividades/{id} → 200 OK o 404
    @GetMapping("/{id}")
    public ResponseEntity<Actividad> obtenerPorId(@PathVariable Long id) {
        return actividadService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/actividades → 201 Created
    @PostMapping
    public ResponseEntity<Actividad> crear(@Valid @RequestBody Actividad actividad) {
        Actividad nueva = actividadService.guardar(actividad);
        return ResponseEntity.status(201).body(nueva);
    }

    // PUT /api/actividades/{id} → 200 OK o 404
    @PutMapping("/{id}")
    public ResponseEntity<Actividad> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Actividad actividad) {
        return actividadService.actualizar(id, actividad)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/actividades/{id} → 204 o 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (actividadService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        actividadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
