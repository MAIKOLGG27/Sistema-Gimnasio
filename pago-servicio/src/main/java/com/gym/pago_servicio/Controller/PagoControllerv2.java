package com.gym.pago_servicio.Controller;

import com.gym.pago_servicio.model.Pago;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.gym.pago_servicio.Service.PagoService;
import com.gym.pago_servicio.assemblers.PagoModelAssembler;



@RestController
@RequestMapping("/pagos")
public class PagoControllerv2 {

    @Autowired
    private PagoService servicio;
    @Autowired
    private PagoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Pago>> getAllPagos() {
    List<EntityModel<Pago>> pagos = servicio.obtenerTodas()
            .stream()
            .map(assembler::toModel)
            .toList();
    return CollectionModel.of(pagos,
            linkTo(methodOn(PagoControllerv2.class).getAllPagos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Pago> getPagoById(@PathVariable Long id) {
    Pago pago = servicio.buscarPorId(id)
            .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado con ID: " + id));
    return assembler.toModel(pago);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pago>> createPago(@RequestBody Pago pago) {
        Pago newPago = servicio.registrarPago(pago);
        return ResponseEntity
                .created(linkTo(methodOn(PagoControllerv2.class).getPagoById(newPago.getId())).toUri())
                .body(assembler.toModel(newPago));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pago>> updateCarrera(@PathVariable long id, @RequestBody Pago pago) {
        pago.setId(id);
        Pago actualizarPago = servicio.actualizarPago(id, pago);
        return ResponseEntity
                .ok(assembler.toModel(actualizarPago));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> borrarPago(@PathVariable long id) {
        servicio.borrarPago(id);
        return ResponseEntity.noContent().build();
    }

}
