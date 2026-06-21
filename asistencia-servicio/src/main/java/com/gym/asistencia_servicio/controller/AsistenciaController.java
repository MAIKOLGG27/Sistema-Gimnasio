package com.gym.asistencia_servicio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.asistencia_servicio.model.AsistenciaModel;
import com.gym.asistencia_servicio.service.AsistenciaService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/asistencia")
@Tag(name = "Asistencias", description = "Controladores del funcionamiento de las asistencias")
public class AsistenciaController {

    @Autowired
    private AsistenciaService servicio;

    @Operation(summary = "Listar todas las asistencias")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @GetMapping("/Listar")
    public List<AsistenciaModel> traerTodos(){
        return servicio.obtenerTodas();
    }

    @Operation(summary = "Buscar asistencia por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recurso encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @GetMapping("/{id}")
    public AsistenciaModel buscarPorId(@PathVariable Long id){
        AsistenciaModel asistencia = servicio.buscarPorId(id);
        if (asistencia == null) {
            throw new EntityNotFoundException("Asistencia no encontrada con ID: " + id);
        }
        return asistencia;
    }

    @Operation(summary = "Registrar una nueva asistencia")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Asistencia creada"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "409", description = "Asistencia ya registrada")
    })
    @PostMapping("/Registrar")
    public AsistenciaModel guardarAsistencia(@RequestBody AsistenciaModel asistencia){
        return servicio.registrarAsistencia(asistencia);
    }

    @Operation(summary = "Actualizar asistencia existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @PutMapping("/Actualizar/{id}")
    public AsistenciaModel actualizar(@PathVariable Long id, @RequestBody AsistenciaModel asistencia){
        AsistenciaModel actualizada = servicio.actualizarAsistencia(id, asistencia);
        if (actualizada == null) {
            throw new EntityNotFoundException("Asistencia no encontrada con ID: " + id);
        }
        return actualizada;
    }

    @Operation(summary = "Borrar asistencia por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Asistencia borrada"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @DeleteMapping("/Eliminar/{id}")
    public void eliminar(@PathVariable Long id){
        servicio.borrarAsistencia(id);
    }

}
