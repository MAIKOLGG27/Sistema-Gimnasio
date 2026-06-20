package cl.gym.ActividadesDeportivas.assembler;

import cl.gym.ActividadesDeportivas.controller.ActividadController;
import cl.gym.ActividadesDeportivas.model.Actividad;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ActividadAssembler implements RepresentationModelAssembler<Actividad, EntityModel<Actividad>> {

    @Override
    public EntityModel<Actividad> toModel(Actividad actividad) {
        return EntityModel.of(actividad,
                linkTo(methodOn(ActividadController.class).obtenerPorId(actividad.getIdActividad())).withSelfRel(),
                linkTo(methodOn(ActividadController.class).obtenerTodos()).withRel("todas-las-actividades"),
                linkTo(methodOn(ActividadController.class).eliminar(actividad.getIdActividad())).withRel("eliminar")
        );
    }
}