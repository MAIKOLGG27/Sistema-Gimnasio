package com.gym.contrato_servicio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.contrato_servicio.model.ContratoModel;
import com.gym.contrato_servicio.service.ContratoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/contratos")
public class ContratoController {

    @Autowired
    private ContratoService servicio;

    @GetMapping("/Listar")
    public List<ContratoModel> traerTodos(){
        return servicio.obtenerTodas();
    }

    @PostMapping("/crear")
    public ContratoModel crearContrato(@RequestBody ContratoModel contrato) {
        return servicio.registrarContrato(contrato);
    }

    @PutMapping("/actualizar/{id}")
    public ContratoModel actualizar(@PathVariable Long id, @RequestBody ContratoModel contrato){
        return servicio.actualizarContrato(id, contrato);
    }

    @DeleteMapping("/borrar/{id}")
    public void eliminarContrato(@PathVariable Long id){
        servicio.borrarContrato(id);
    }
    
    

}
