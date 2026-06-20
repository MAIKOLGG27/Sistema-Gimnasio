package cl.gym.ActividadesDeportivas.controller;


import cl.gym.ActividadesDeportivas.assembler.ActividadAssembler;
import cl.gym.ActividadesDeportivas.model.Actividad;
import cl.gym.ActividadesDeportivas.service.ActividadService;
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
@RequestMapping("/api/actividades")
@RequiredArgsConstructor
@Tag(name = "Gestión de Actividades", description = "Endpoints para administrar las actividades deportivas del gimnasio")
public class ActividadController {

    private final ActividadService actividadService;
    private final ActividadAssembler actividadAssembler;

    @Operation(summary = "Obtener todas las actividades")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Actividad>>> obtenerTodos() {
        List<EntityModel<Actividad>> actividades = actividadService.obtenerTodos().stream()
                .map(actividadAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(actividades,
                linkTo(methodOn(ActividadController.class).obtenerTodos()).withSelfRel()));
    }

    @Operation(summary = "Obtener actividad por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actividad encontrada"),
            @ApiResponse(responseCode = "404", description = "La actividad no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Actividad>> obtenerPorId(
            @Parameter(description = "ID de la actividad", example = "1")
            @PathVariable Long id) {
        return actividadService.obtenerPorId(id)
                .map(actividadAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nueva actividad")
    @ApiResponse(responseCode = "201", description = "Actividad creada exitosamente")
    @PostMapping
    public ResponseEntity<EntityModel<Actividad>> crear(@Valid @RequestBody Actividad actividad) {
        Actividad nueva = actividadService.guardar(actividad);
        return ResponseEntity.status(HttpStatus.CREATED).body(actividadAssembler.toModel(nueva));
    }

    @Operation(summary = "Actualizar actividad")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actividad actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "La actividad no existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Actividad>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Actividad actividad) {
        return actividadService.actualizar(id, actividad)
                .map(actividadAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar actividad")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Actividad eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "La actividad no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {
        if (actividadService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        actividadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}