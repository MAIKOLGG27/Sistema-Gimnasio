package com.gym.progreso_servicio.controller;

import com.gym.progreso_servicio.model.Progreso;
import com.gym.progreso_servicio.service.ProgresoService;
import com.gym.progreso_servicio.Assembler.AssemblerProgreso;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/progreso")
@RequiredArgsConstructor
public class ProgresoController {

    private final ProgresoService progresoService;
    private final AssemblerProgreso assembler;

    @Operation(summary = "Lista todos los progresos registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Progreso>>> obtenerTodos() {
        List<EntityModel<Progreso>> progresos = progresoService.obtenerTodos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(progresos,
            linkTo(methodOn(ProgresoController.class).obtenerTodos()).withSelfRel()));
    }

    @Operation(summary = "Obtiene un registro de progreso por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Progreso>> obtenerPorId(@PathVariable Long id) {
        return progresoService.obtenerPorId(id)
                .map(progreso -> ResponseEntity.ok(assembler.toModel(progreso)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtiene los registros de progreso de un cliente específico")
    @GetMapping("/cliente/{clientId}")
    public ResponseEntity<CollectionModel<EntityModel<Progreso>>> obtenerPorClienteId(@PathVariable Long clienteId) {
         List<EntityModel<Progreso>> progresos = progresoService.obtenerPorClienteId(clienteId).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(progresos,
            linkTo(methodOn(ProgresoController.class).obtenerPorClienteId(clienteId)).withSelfRel()));
    }

    @Operation(summary = "Crea un nuevo registro de progreso")
    @PostMapping
    public ResponseEntity<EntityModel<Progreso>> crearProgreso(@RequestBody Progreso progreso) {
        Progreso creado = progresoService.crearProgreso(progreso);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(creado));
    }

    @Operation(summary = "Actualiza un registro de progreso")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Progreso>> actualizarProgreso(@PathVariable Long id, @RequestBody Progreso nuevoProgreso) {
        return progresoService.actualizarProgreso(id, nuevoProgreso)
                .map(progreso -> ResponseEntity.ok(assembler.toModel(progreso)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Elimina un registro de progreso por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProgreso(@PathVariable Long id) {
        progresoService.eliminarProgreso(id);
        return ResponseEntity.noContent().build();
    }
}