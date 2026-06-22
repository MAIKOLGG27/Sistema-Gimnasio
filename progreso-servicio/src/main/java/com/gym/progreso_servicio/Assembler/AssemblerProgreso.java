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

    // Ruta del Gateway hacia el microservicio de clientes
    private static final String GATEWAY_URL_CLIENTE = "http://localhost:8080/api/clientes/";

    @Override
    public EntityModel<Progreso> toModel(Progreso progreso) {
        
        Link linkCliente = Link.of(GATEWAY_URL_CLIENTE + progreso.getClienteId()).withRel("cliente-info");

        return EntityModel.of(progreso,
            linkTo(methodOn(ProgresoController.class).obtenerPorId(progreso.getId())).withSelfRel(),
            linkTo(methodOn(ProgresoController.class).obtenerTodos()).withRel("todos-los-progresos"),
            linkCliente
        );
    }
}