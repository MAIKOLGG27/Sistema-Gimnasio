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

    // Rutas del Gateway hacia los otros microservicios (Ajusta el puerto 8080 si tu Gateway usa otro)
    private static final String GATEWAY_URL_CLIENTE = "http://localhost:8080/api/clientes/";
    private static final String GATEWAY_URL_MEMBRESIA = "http://localhost:8080/api/membresias/";

    @Override
    public EntityModel<ContratoModel> toModel(ContratoModel contrato) {
        
        // Enlaces externos a los microservicios de tus compañeros y al tuyo de membresías
        Link linkCliente = Link.of(GATEWAY_URL_CLIENTE + contrato.getUsuarioId()).withRel("cliente-info");
        Link linkMembresia = Link.of(GATEWAY_URL_MEMBRESIA + contrato.getMebresiaId()).withRel("membresia-info");

        return EntityModel.of(contrato,
            linkTo(methodOn(ContratoController.class).obtenerPorId(contrato.getId())).withSelfRel(),
            linkTo(methodOn(ContratoController.class).traerTodos()).withRel("todos-los-contratos"),
            linkCliente,
            linkMembresia
        );
    }
}