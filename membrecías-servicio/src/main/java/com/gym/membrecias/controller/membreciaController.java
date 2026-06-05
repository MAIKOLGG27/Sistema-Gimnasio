package com.gym.membrecias.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.membrecias.model.membreciaModel;
import com.gym.membrecias.service.membreciaService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/membresias")
public class membreciaController {

    @Autowired
    private membreciaService servicio;

    @GetMapping("/Listar")
    public List<membreciaModel> traerTodos(){
        return servicio.obtenerTodas();
    }
    @Operation(summary = "obtiene un producto en base a una id otorgada")
    @GetMapping("/{id}")
    public membreciaModel obtenerPorId(@PathVariable Long id){
        return servicio.obtenerPorId(id);
    }
    

    @PostMapping("/crear")
    public membreciaModel guardarMembresia(@RequestBody membreciaModel mem) {
        return servicio.guardarMembresia(mem);
    }

    @PutMapping("/actualizar/{id}")
    public membreciaModel actualizar(@PathVariable Long id, @RequestBody membreciaModel membresia){
        return servicio.actualizarMembrecia(id, membresia);
    }

    @DeleteMapping("/borrar/{id}")
    public void borrarMembresia(@PathVariable long id){
        servicio.borrarMembresia(id);;
    }
    

}
