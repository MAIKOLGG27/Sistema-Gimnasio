package com.gym.registro_servicio.assemblers;

import com.gym.registro_servicio.Controller.RegistroControllerv2;
import com.gym.registro_servicio.Model.Registro;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RegistroModelAssembler extends RepresentationModelAssemblerSupport<Registro, EntityModel<Registro>> {

    public RegistroModelAssembler() {
        super(RegistroControllerv2.class, (Class<EntityModel<Registro>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<Registro> toModel(Registro registro) {
        return EntityModel.of(registro,
                linkTo(methodOn(RegistroControllerv2.class).getAllRegistros()).withRel("registros"));
    }
}
