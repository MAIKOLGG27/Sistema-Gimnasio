package cl.gym.reservas.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.gym.reservas.assembler.ReservaAssembler;
import cl.gym.reservas.model.Reserva;
import cl.gym.reservas.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;
    private final ReservaAssembler reservaAssembler;

    // GET /api/reservas → 200 OK con lista
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Reserva>>> obtenerTodos() {
        List<EntityModel<Reserva>> reservas = reservaService.obtenerTodos()
                .stream()
                .map(reservaAssembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(reservas,
                linkTo(methodOn(ReservaController.class).obtenerTodos()).withSelfRel()));
    }

    // GET /api/reservas/{id} → 200 OK o 404
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Reserva>> obtenerPorId(@PathVariable Long id) {
        return reservaService.obtenerPorId(id)
                .map(reservaAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/reservas/cliente/{clienteId}
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Reserva>> obtenerPorClienteId(@PathVariable Long clienteId) {
        return ResponseEntity.ok(reservaService.obtenerPorClienteId(clienteId));
    }

    // GET /api/reservas/estado/{estado}
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Reserva>> obtenerPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(reservaService.obtenerPorEstado(estado));
    }

    // POST /api/reservas → 201 Created
    @PostMapping
    public ResponseEntity<EntityModel<Reserva>> crear(@Valid @RequestBody Reserva reserva) {
        Reserva nueva = reservaService.guardar(reserva);
        return ResponseEntity.status(201).body(reservaAssembler.toModel(nueva));
    }

    // PUT /api/reservas/{id} → 200 OK o 404
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Reserva>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Reserva reserva) {
        return reservaService.actualizar(id, reserva)
                .map(reservaAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/reservas/{id} → 204 o 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (reservaService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}