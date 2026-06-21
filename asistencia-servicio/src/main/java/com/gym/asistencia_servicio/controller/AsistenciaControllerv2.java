package com.gym.asistencia_servicio.controller;

import com.gym.asistencia_servicio.model.AsistenciaModel;
import com.gym.asistencia_servicio.service.AsistenciaService;
import com.gym.asistencia_servicio.assemblers.AsistenciaModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("api/v2/asistencias")
public class AsistenciaControllerv2 {

    @Autowired
    private AsistenciaService servicio;

    @Autowired
    private AsistenciaModelAssembler assembler;

    @Operation(summary = "Listar asistencias")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<AsistenciaModel>> getAllAsistencias() {
        List<EntityModel<AsistenciaModel>> asistencias = servicio.obtenerTodas()
                .stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(asistencias,
                linkTo(methodOn(AsistenciaControllerv2.class).getAllAsistencias()).withSelfRel());
    }

    @Operation(summary = "Buscar asistencia por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recurso encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<AsistenciaModel> getAsistenciaById(@PathVariable Long id) {
        AsistenciaModel asistencia = servicio.buscarPorId(id);
        if (asistencia == null) {
            throw new EntityNotFoundException("Asistencia no encontrada con ID: " + id);
        }
        return assembler.toModel(asistencia);
    }

    @Operation(summary = "Registrar una nueva asistencia")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Asistencia creada"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "409", description = "Asistencia ya registrada")
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<AsistenciaModel>> createAsistencia(@RequestBody AsistenciaModel asistencia) {
        AsistenciaModel nuevaAsistencia = servicio.registrarAsistencia(asistencia);
        return ResponseEntity
                .created(linkTo(methodOn(AsistenciaControllerv2.class).getAsistenciaById(nuevaAsistencia.getId())).toUri())
                .body(assembler.toModel(nuevaAsistencia));
    }

    @Operation(summary = "Actualizar asistencia existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<AsistenciaModel>> updateAsistencia(@PathVariable Long id,
                                                                          @RequestBody AsistenciaModel asistencia) {
        AsistenciaModel actualizada = servicio.actualizarAsistencia(id, asistencia);
        if (actualizada == null) {
            throw new EntityNotFoundException("Asistencia no encontrada con ID: " + id);
        }
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

    @Operation(summary = "Borrar asistencia por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Asistencia eliminada"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> borrarAsistencia(@PathVariable Long id) {
        servicio.borrarAsistencia(id);
        return ResponseEntity.noContent().build();
    }
}
