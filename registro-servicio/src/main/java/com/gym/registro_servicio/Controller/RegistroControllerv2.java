package com.gym.registro_servicio.Controller;

import com.gym.registro_servicio.Model.Registro;

import com.gym.registro_servicio.Service.RegistroService;
import com.gym.registro_servicio.assemblers.RegistroModelAssembler;

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
@RequestMapping("api/v2/registros")
public class RegistroControllerv2 {

    @Autowired
    private RegistroService servicio;

    @Autowired
    private RegistroModelAssembler assembler;

    @Operation(summary = "Listar registros")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Registro>> getAllRegistros() {
        List<EntityModel<Registro>> registros = servicio.obtenerTodas()
                .stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(registros,
                linkTo(methodOn(RegistroControllerv2.class).getAllRegistros()).withSelfRel());
    }

    @Operation(summary = "Buscar registro por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recurso encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Registro> getRegistroById(@PathVariable Long id) {
        Registro registro = servicio.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro no encontrado con ID: " + id));
        return assembler.toModel(registro);
    }

    @Operation(summary = "Registrar un nuevo socio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Registro creado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "409", description = "Registro ya existente")
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Registro>> createRegistro(@RequestBody Registro registro) {
        Registro nuevoRegistro = servicio.registrarRegistro(registro);
        return ResponseEntity
                .created(linkTo(methodOn(RegistroControllerv2.class).getRegistroById(nuevoRegistro.getId())).toUri())
                .body(assembler.toModel(nuevoRegistro));
    }

    @Operation(summary = "Actualizar registro existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Registro>> updateRegistro(@PathVariable long id,
                                                                 @RequestBody Registro registro) {
        registro.setId(id);
        Registro actualizado = servicio.actualizarRegistro(id, registro);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @Operation(summary = "Borrar registro por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Registro eliminado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> borrarRegistro(@PathVariable long id) {
        servicio.borrarRegistro(id);
        return ResponseEntity.noContent().build();
    }
}
