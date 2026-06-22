package com.gym.contrato_servicio.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gym.contrato_servicio.model.ContratoModel;
import com.gym.contrato_servicio.service.ContratoService;
import com.gym.contrato_servicio.Assembler.AssemblerContrato;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/contratos")
public class ContratoController {

    @Autowired
    private ContratoService servicio;

    @Autowired
    private AssemblerContrato assembler;

    @Operation(summary = "Obtiene un contrato por su ID con enlaces HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ContratoModel>> obtenerPorId(@PathVariable Long id) {
        ContratoModel contrato = servicio.obtenerPorId(id);
        return ResponseEntity.ok(assembler.toModel(contrato));
    }

    @Operation(summary = "Lista todos los contratos registrados")
    @GetMapping("/Listar")
    public ResponseEntity<CollectionModel<EntityModel<ContratoModel>>> traerTodos() {
        List<EntityModel<ContratoModel>> contratos = servicio.obtenerTodas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(contratos,
            linkTo(methodOn(ContratoController.class).traerTodos()).withSelfRel()));
    }

    @Operation(summary = "Crea un nuevo contrato")
    @PostMapping("/crear")
    public ContratoModel crearContrato(@RequestBody ContratoModel contrato) {
        return servicio.registrarContrato(contrato);
    }

    @Operation(summary = "Actualiza la información de un contrato existente")
    @PutMapping("/actualizar/{id}")
    public ContratoModel actualizar(@PathVariable Long id, @RequestBody ContratoModel contrato){
        return servicio.actualizarContrato(id, contrato);
    }

    @Operation(summary = "Elimina un contrato por su ID")
    @DeleteMapping("/borrar/{id}")
    public void eliminarContrato(@PathVariable Long id){
        servicio.borrarContrato(id);
    }
}