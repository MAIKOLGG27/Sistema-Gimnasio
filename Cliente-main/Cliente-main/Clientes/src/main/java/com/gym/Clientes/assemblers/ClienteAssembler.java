package com.gym.Clientes.assemblers;

import com.gym.Clientes.controller.ClienteController;
import com.gym.Clientes.model.cliente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClienteAssembler implements RepresentationModelAssembler<cliente, EntityModel<cliente>> {

    @Override
    public EntityModel<cliente> toModel(cliente cli) {
        return EntityModel.of(cli,
                linkTo(methodOn(ClienteController.class).obtenerPorId(cli.getId())).withSelfRel(),
                linkTo(methodOn(ClienteController.class).obtenerTodos()).withRel("todos-los-clientes"),
                linkTo(methodOn(ClienteController.class).eliminarCliente(cli.getId())).withRel("eliminar")
        );
    }
}