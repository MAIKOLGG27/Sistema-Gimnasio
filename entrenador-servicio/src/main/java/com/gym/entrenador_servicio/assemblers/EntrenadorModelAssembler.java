package com.gym.entrenador_servicio.assemblers;

import com.gym.entrenador_servicio.Controller.EntrenadorControllerv2;
import com.gym.entrenador_servicio.Model.Entrenador;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EntrenadorModelAssembler extends RepresentationModelAssemblerSupport<Entrenador, EntityModel<Entrenador>> {

    public EntrenadorModelAssembler() {
        super(EntrenadorControllerv2.class, (Class<EntityModel<Entrenador>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<Entrenador> toModel(Entrenador entrenador) {
        return EntityModel.of(entrenador,
                linkTo(methodOn(EntrenadorControllerv2.class).getAllEntrenadores()).withRel("entrenadores"));
    }
}
