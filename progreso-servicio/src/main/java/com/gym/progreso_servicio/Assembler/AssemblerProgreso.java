package com.gym.progreso_servicio.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.gym.progreso_servicio.controller.ProgresoController;
import com.gym.progreso_servicio.model.Progreso;

@Component
public class AssemblerProgreso implements RepresentationModelAssembler<Progreso, EntityModel<Progreso>> {

    
    private static final String CLIENTE_URL = "http://localhost:8087/clientes/";

    @Override
    public EntityModel<Progreso> toModel(Progreso progreso) {
        
        
        Link linkCliente = Link.of(CLIENTE_URL + progreso.getClienteId()).withRel("cliente-info");

        return EntityModel.of(progreso,
            linkTo(methodOn(ProgresoController.class).obtenerPorId(progreso.getId())).withSelfRel(),
            linkTo(methodOn(ProgresoController.class).obtenerTodos()).withRel("todos-los-progresos"),
            linkCliente
        );
    }
}