package com.gym.progreso_servicio.controller;

import com.gym.progreso_servicio.model.Progreso;
import com.gym.progreso_servicio.service.ProgresoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progreso")
@RequiredArgsConstructor
public class ProgresoController {

    private final ProgresoService progresoService;

    @GetMapping
    public List<Progreso> obtenerTodos() {
        return progresoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Progreso> obtenerPorId(@PathVariable Long id) {
        return progresoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clientId}")
    public List<Progreso> obtenerPorClienteId(@PathVariable Long clientId) {
        return progresoService.obtenerPorClienteId(clientId);
    }

    @PostMapping
    public ResponseEntity<Progreso> crearProgreso(@RequestBody Progreso progreso) {
        Progreso creado = progresoService.crearProgreso(progreso);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Progreso> actualizarProgreso(@PathVariable Long id, @RequestBody Progreso nuevoProgreso) {
        return progresoService.actualizarProgreso(id, nuevoProgreso)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProgreso(@PathVariable Long id) {
        progresoService.eliminarProgreso(id);
        return ResponseEntity.noContent().build();
    }
}