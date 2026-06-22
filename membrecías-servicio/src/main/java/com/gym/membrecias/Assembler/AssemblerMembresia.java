package com.gym.membrecias.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.gym.membrecias.controller.membreciaController;
import com.gym.membrecias.model.membreciaModel;

@Component
public class AssemblerMembresia implements RepresentationModelAssembler<membreciaModel, EntityModel<membreciaModel>> {


    private static final String ASISTENCIAS_URL = "http://localhost:8083/asistencias/membresia/";

    @Override
    public EntityModel<membreciaModel> toModel(membreciaModel membresia) {
        
        
        Link linkAsistencias = Link.of(ASISTENCIAS_URL + membresia.getId()).withRel("asistencias");

        return EntityModel.of(membresia,
            // Estos dos links sí funcionan con methodOn porque apuntan a tu propio controlador
            linkTo(methodOn(membreciaController.class).obtenerPorId(membresia.getId())).withSelfRel(),
            linkTo(methodOn(membreciaController.class).listarTodas()).withRel("todas-membresias"),
            
            
            linkAsistencias
        );
    }
}
