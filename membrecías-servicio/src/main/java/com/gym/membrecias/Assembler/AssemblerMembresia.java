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

    // PRESTA ATENCIÓN AQUÍ: 
    // Cambia el "8080" por el puerto donde corra el proyecto "Gateway" de tu imagen.
    // Cambia "/api/asistencias" por la ruta real que hayan configurado en el Gateway para llegar al servicio de tu compañero.
    private static final String GATEWAY_URL_ASISTENCIAS = "http://localhost:8080/api/asistencias/membresia/";

    @Override
    public EntityModel<membreciaModel> toModel(membreciaModel membresia) {
        
        // Creamos el enlace manualmente apuntando al microservicio de tu compañero a través del Gateway
        Link linkAsistencias = Link.of(GATEWAY_URL_ASISTENCIAS + membresia.getId()).withRel("asistencias");

        return EntityModel.of(membresia,
            // Estos dos links sí funcionan con methodOn porque apuntan a tu propio controlador
            linkTo(methodOn(membreciaController.class).obtenerPorId(membresia.getId())).withSelfRel(),
            linkTo(methodOn(membreciaController.class).listarTodas()).withRel("todas-membresias"),
            
            // Aquí agregamos el enlace externo que creamos arriba
            linkAsistencias
        );
    }
}
