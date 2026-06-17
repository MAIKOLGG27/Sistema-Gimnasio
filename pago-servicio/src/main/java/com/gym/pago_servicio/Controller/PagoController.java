package com.gym.pago_servicio.Controller;


import com.gym.pago_servicio.model.Pago;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.gym.pago_servicio.Service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagos")

@Tag(name = "Pagos" , description = "Controladores del funcionamiento de los pagos")
public class PagoController {

    @Autowired
    private PagoService servicio;

    @Operation(summary = "Listar todos los pagos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @GetMapping("/listar")
    public List<Pago> traerTodos(){
        return servicio.obtenerTodas();
    }

    @Operation(summary = "Buscar pago por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recurso encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @GetMapping("/{id}")                    
    public Optional<Pago> buscarPorId(@PathVariable Long id){
        return servicio.buscarPorId(id);
    }

    @Operation(summary = "Registrar pagos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pago creado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "409", description = "Pago ya registrado")
    })
    @PostMapping("/crear")
    public Pago crearPago(@Valid @RequestBody Pago pago) {
        return servicio.registrarPago(pago);
    }

    @Operation(summary = "Actualizar pago existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion Exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @PutMapping("/{id}")                    
    public Pago actualizarPago(@PathVariable Long id, 
                                @Valid @RequestBody Pago pago){
        return servicio.actualizarPago(id, pago);
    }
    
    @Operation(summary = "Borrar pago por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pago borrado exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @DeleteMapping("/borrar/{id}")
    public void eliminarPago(@PathVariable Long id){
        servicio.borrarPago(id);
    }

    @Operation(summary = "Buscar pagos por socio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se encontraron los datos del socio "),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @GetMapping("/socio/{socioId}")
    public List<Pago> buscarPorSocio(@PathVariable Long socioId) {
        return servicio.buscarPorSocioId(socioId);
    }
}
