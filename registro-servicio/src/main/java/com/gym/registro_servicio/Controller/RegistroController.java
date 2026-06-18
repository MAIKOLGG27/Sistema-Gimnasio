package com.gym.registro_servicio.Controller;

import com.gym.registro_servicio.Model.Registro;
import com.gym.registro_servicio.Service.RegistroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/v0/registros")
@Tag(name = "Registros", description = "Controladores del funcionamiento de los registros de socios")
public class RegistroController {

    @Autowired
    private RegistroService servicio;

    @Operation(summary = "Listar todos los registros")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @GetMapping("/listar")
    public List<Registro> traerTodos() {
        return servicio.obtenerTodas();
    }

    @Operation(summary = "Buscar registro por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recurso encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @GetMapping("/{id}")
    public Optional<Registro> buscarPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id);
    }

    @Operation(summary = "Registrar un nuevo socio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Registro creado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "409", description = "Registro ya existente")
    })
    @PostMapping("/crear")
    public Registro crearRegistro(@RequestBody Registro registro) {
        return servicio.registrarRegistro(registro);
    }

    @Operation(summary = "Actualizar registro existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operacion exitosa"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @PutMapping("/{id}")
    public Registro actualizarRegistro(@PathVariable Long id,
                                       @RequestBody Registro registro) {
        return servicio.actualizarRegistro(id, registro);
    }

    @Operation(summary = "Borrar registro por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Registro borrado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @DeleteMapping("/borrar/{id}")
    public void eliminarRegistro(@PathVariable Long id) {
        servicio.borrarRegistro(id);
    }

    @Operation(summary = "Buscar registro junto con su pago asociado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se encontraron los datos del registro con su pago"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @GetMapping("/conPago/{id}")
    public Map buscarConPago(@PathVariable Long id) {
        return servicio.obtenerRegistroConPago(id);
    }
}
