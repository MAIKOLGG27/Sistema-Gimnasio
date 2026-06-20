package com.gym.Clientes.controller;

import com.gym.Clientes.assemblers.ClienteAssembler;
import com.gym.Clientes.model.cliente;
import com.gym.Clientes.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Gestión de Clientes", description = "Endpoints para administrar los clientes del gimnasio")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteAssembler clienteAssembler;

    // ── GET /api/usuarios ──────────────────────────────────────────────────
    @Operation(summary = "Obtener todos los clientes", description = "Retorna una lista completa de todos los clientes registrados.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<cliente>>> obtenerTodos() {
        List<EntityModel<cliente>> clientes = clienteService.obtenerTodos().stream()
                .map(clienteAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(clientes,
                linkTo(methodOn(ClienteController.class).obtenerTodos()).withSelfRel()));
    }

    // ── GET /api/usuarios/{id} ─────────────────────────────────────────────
    @Operation(summary = "Obtener cliente por ID", description = "Retorna el cliente con el ID indicado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El cliente indicado no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<cliente>> obtenerPorId(
            @Parameter(description = "Identificador del cliente a consultar", example = "1")
            @PathVariable Long id) {
        return clienteService.obtenerPorId(id)
                .map(clienteAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── POST /api/usuarios ─────────────────────────────────────────────────
    @Operation(summary = "Crear nuevo cliente", description = "Se guardará el cliente con un ID autoincremental y los datos ingresados.")
    @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente")
    @PostMapping
    public ResponseEntity<EntityModel<cliente>> crearCliente(@Valid @RequestBody cliente cli) {
        cliente nuevo = clienteService.crearCliente(cli);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteAssembler.toModel(nuevo));
    }

    // ── PUT /api/usuarios/{id} ─────────────────────────────────────────────
    @Operation(summary = "Actualizar cliente", description = "Se actualizará el cliente según el ID indicado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "El cliente indicado no existe"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<cliente>> actualizarCliente(
            @Parameter(description = "ID del cliente que se desea actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody cliente cli) {
        return clienteService.actualizarCliente(id, cli)
                .map(clienteAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── DELETE /api/usuarios/{id} ──────────────────────────────────────────
    @Operation(summary = "Eliminar cliente", description = "Se eliminará el cliente según el ID indicado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente eliminado con éxito (No Content)"),
            @ApiResponse(responseCode = "404", description = "El cliente no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(
            @Parameter(description = "ID del cliente que se desea eliminar", example = "1")
            @PathVariable Long id) {
        if (clienteService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    
}