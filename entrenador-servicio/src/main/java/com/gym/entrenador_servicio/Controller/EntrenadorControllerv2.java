package com.gym.entrenador_servicio.Controller;

import com.gym.entrenador_servicio.Model.Entrenador;
import com.gym.entrenador_servicio.Service.EntrenadorService;
import com.gym.entrenador_servicio.assemblers.EntrenadorModelAssembler;

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
@RequestMapping("api/v2/entrenadores")
public class EntrenadorControllerv2 {

    @Autowired
    private EntrenadorService servicio;

    @Autowired
    private EntrenadorModelAssembler assembler;

    @Operation(summary = "Listar entrenadores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Entrenador>> getAllEntrenadores() {
        List<EntityModel<Entrenador>> entrenadores = servicio.obtenerTodas()
                .stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(entrenadores,
                linkTo(methodOn(EntrenadorControllerv2.class).getAllEntrenadores()).withSelfRel());
    }

    @Operation(summary = "Buscar entrenador por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recurso encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Entrenador> getEntrenadorById(@PathVariable Long id) {
        Entrenador entrenador = servicio.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Entrenador no encontrado con ID: " + id));
        return assembler.toModel(entrenador);
    }

    @Operation(summary = "Registrar un nuevo entrenador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Entrenador creado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "409", description = "Entrenador ya registrado")
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Entrenador>> createEntrenador(@RequestBody Entrenador entrenador) {
        Entrenador nuevoEntrenador = servicio.registrarEntrenador(entrenador);
        return ResponseEntity
                .created(linkTo(methodOn(EntrenadorControllerv2.class).getEntrenadorById(nuevoEntrenador.getId())).toUri())
                .body(assembler.toModel(nuevoEntrenador));
    }

    @Operation(summary = "Actualizar entrenador existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Entrenador>> updateEntrenador(@PathVariable long id,
                                                                     @RequestBody Entrenador entrenador) {
        entrenador.setId(id);
        Entrenador actualizado = servicio.actualizarEntrenador(id, entrenador);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @Operation(summary = "Borrar entrenador por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Entrenador eliminado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> borrarEntrenador(@PathVariable long id) {
        servicio.borrarEntrenador(id);
        return ResponseEntity.noContent().build();
    }
}
