package cl.gym.reservas.assembler;

import cl.gym.reservas.controller.ReservaController;
import cl.gym.reservas.model.Reserva;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReservaAssembler implements RepresentationModelAssembler<Reserva, EntityModel<Reserva>> {

    @Override
    public EntityModel<Reserva> toModel(Reserva reserva) {
        return EntityModel.of(reserva,
                linkTo(methodOn(ReservaController.class)
                        .obtenerPorId(reserva.getCodigoReserva())).withSelfRel(),
                linkTo(methodOn(ReservaController.class)
                        .obtenerTodos()).withRel("todas-las-reservas"));
    }
}