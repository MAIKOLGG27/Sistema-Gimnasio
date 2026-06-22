package com.gym.membrecias.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gym.membrecias.model.membreciaModel;
import com.gym.membrecias.service.membreciaService;
import com.gym.membrecias.Assembler.AssemblerMembresia;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/membresias")
public class membreciaController {

    // 1. Añadimos @Autowired para que Spring inyecte el servicio
    @Autowired
    private membreciaService servicio;

    
    @Autowired
    private AssemblerMembresia assembler;

    
    @Operation(summary = "Obtiene una membresía en base a una ID otorgada")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<membreciaModel>> obtenerPorId(@PathVariable Long id) {
        membreciaModel membresia = servicio.obtenerPorId(id);
        
        return ResponseEntity.ok(assembler.toModel(membresia));
    }

    
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<membreciaModel>>> listarTodas() {
        List<EntityModel<membreciaModel>> membresias = servicio.obtenerTodas().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
            
        return ResponseEntity.ok(CollectionModel.of(membresias,
            linkTo(methodOn(membreciaController.class).listarTodas()).withSelfRel()));
    }

    @PostMapping("/crear")
    public membreciaModel guardarMembresia(@RequestBody membreciaModel mem) {
        return servicio.guardarMembresia(mem);
    }

    @PutMapping("/actualizar/{id}")
    public membreciaModel actualizar(@PathVariable Long id, @RequestBody membreciaModel membresia){
        return servicio.actualizarMembrecia(id, membresia);
    }

    @DeleteMapping("/borrar/{id}")
    public void borrarMembresia(@PathVariable long id){
        servicio.borrarMembresia(id);
    }
}
