package com.gym.contrato_servicio.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.gym.contrato_servicio.controller.ContratoController;
import com.gym.contrato_servicio.model.ContratoModel;

@Component
public class AssemblerContrato implements RepresentationModelAssembler<ContratoModel, EntityModel<ContratoModel>> {

    
    private static final String MEMBRESIA_URL = "http://localhost:8081/membresias/";
    private static final String CLIENTE_URL = "http://localhost:8087/clientes/";

    @Override
    public EntityModel<ContratoModel> toModel(ContratoModel contrato) {
        
       
        Link linkCliente = Link.of(CLIENTE_URL + contrato.getUsuarioId()).withRel("cliente-info");
        Link linkMembresia = Link.of(MEMBRESIA_URL + contrato.getMebresiaId()).withRel("membresia-info");

        return EntityModel.of(contrato,
            linkTo(methodOn(ContratoController.class).obtenerPorId(contrato.getId())).withSelfRel(),
            linkTo(methodOn(ContratoController.class).traerTodos()).withRel("todos-los-contratos"),
            linkCliente,
            linkMembresia
        );
    }
}