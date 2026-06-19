package com.gym.entrenador_servicio.Controller;

import com.gym.entrenador_servicio.Model.Entrenador;
import com.gym.entrenador_servicio.Service.EntrenadorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v0/entrenadores")
@Tag(name = "Entrenadores", description = "Controladores del funcionamiento de los entrenadores")
public class EntrenadorController {

    @Autowired
    private EntrenadorService servicio;

    @Operation(summary = "Listar todos los entrenadores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @GetMapping("/Listar")
    public List<Entrenador> traerTodos() {
        return servicio.obtenerTodas();
    }

    @Operation(summary = "Buscar entrenador por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recurso encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @GetMapping("/{id}")
    public Optional<Entrenador> buscarPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id);
    }

    @Operation(summary = "Registrar un nuevo entrenador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Entrenador creado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "409", description = "Entrenador ya registrado")
    })
    @PostMapping("/crear")
    public Entrenador crearEntrenador(@RequestBody Entrenador entrenador) {
        return servicio.registrarEntrenador(entrenador);
    }

    @Operation(summary = "Actualizar entrenador existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @PutMapping("/{id}")
    public Entrenador actualizarEntrenador(@PathVariable Long id,
                                           @RequestBody Entrenador entrenador) {
        return servicio.actualizarEntrenador(id, entrenador);
    }

    @Operation(summary = "Borrar entrenador por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Entrenador borrado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @DeleteMapping("/borrar/{id}")
    public void eliminarEntrenador(@PathVariable Long id) {
        servicio.borrarEntrenador(id);
    }
}
