package com.gym.pago_servicio.Controller;


import com.gym.pago_servicio.model.Pago;

import io.swagger.v3.oas.models.Operation;

import com.gym.pago_servicio.Service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private PagoService servicio;

    @Operation(summary = "Registrar y guardar pagos")
    @GetMapping("/listar")
    public List<Pago> traerTodos(){
        return servicio.obtenerTodas();
    }

    @GetMapping("/{id}")                    
    public Optional<Pago> buscarPorId(@PathVariable Long id){
        return servicio.buscarPorId(id);
    }

    @PostMapping("/crear")
    public Pago crearPago(@RequestBody Pago pago) {
        return servicio.registrarPago(pago);
    }

    @PutMapping("/{id}")                    
    public Pago actualizarPago(@PathVariable Long id, 
                                @RequestBody Pago pago){
        return servicio.actualizarPago(id, pago);
    }

    @DeleteMapping("/borrar/{id}")
    public void eliminarPago(@PathVariable Long id){
        servicio.borrarPago(id);
    }

    @GetMapping("/socio/{socioId}")
    public List<Pago> buscarPorSocio(@PathVariable Long socioId) {
        return servicio.buscarPorSocioId(socioId);
}
}
