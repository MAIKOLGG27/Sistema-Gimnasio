package com.gym.pago_servicio.assemblers;

import com.gym.pago_servicio.Controller.PagoControllerv2;
import com.gym.pago_servicio.model.Pago;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PagoModelAssembler extends RepresentationModelAssemblerSupport<Pago, EntityModel<Pago>>{
    
    public PagoModelAssembler() {
        super(PagoControllerv2.class, (Class<EntityModel<Pago>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<Pago> toModel(Pago pago) {
        return EntityModel.of(pago,
                linkTo(methodOn(PagoControllerv2.class).getAllPagos()).withRel("pagos"));
    }
}
