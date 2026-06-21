package com.gym.asistencia_servicio.assemblers;

import com.gym.asistencia_servicio.controller.AsistenciaControllerv2;
import com.gym.asistencia_servicio.model.AsistenciaModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AsistenciaModelAssembler extends RepresentationModelAssemblerSupport<AsistenciaModel, EntityModel<AsistenciaModel>> {

    public AsistenciaModelAssembler() {
        super(AsistenciaControllerv2.class, (Class<EntityModel<AsistenciaModel>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<AsistenciaModel> toModel(AsistenciaModel asistencia) {
        return EntityModel.of(asistencia,
                linkTo(methodOn(AsistenciaControllerv2.class).getAllAsistencias()).withRel("asistencias"));
    }
}
